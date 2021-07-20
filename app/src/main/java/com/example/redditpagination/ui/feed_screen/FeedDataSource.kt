package com.example.redditpagination.ui.feed_screen

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.redditpagination.network.data.ApiService
import com.example.redditpagination.network.response.RedditPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

class FeedDataSource @Inject constructor(retrofit: Retrofit): PageKeyedDataSource<String, RedditPost>() {
    private val coroutineContext = Dispatchers.IO

    private val apiService = retrofit.create(ApiService::class.java)

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, RedditPost>
    ) {
        scope.launch {
            try {
                val response = apiService.fetchTopPosts(loadSize = params.requestedLoadSize)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val redditPost = listing?.children?.map { it.data }
                        callback.onResult(redditPost ?: listOf(), listing?.before, listing?.after)
                    }
                }
            } catch (exception: Exception) {
                Log.e("FeedDataSource", "Failed to load data")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RedditPost>
    ) {
        scope.launch {
            try {
                val response = apiService
                    .fetchTopPosts(loadSize = params.requestedLoadSize, before = params.key)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val items = listing?.children?.map { it.data }
                        callback.onResult(items ?: listOf(), listing?.after)
                    }
                }
            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data")
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        scope.launch {
            try {
                val response = apiService
                    .fetchTopPosts(loadSize = params.requestedLoadSize, after = params.key)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val items = listing?.children?.map { it.data }
                        callback.onResult(items ?: listOf(), listing?.after)
                    }
                }
            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data")
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}