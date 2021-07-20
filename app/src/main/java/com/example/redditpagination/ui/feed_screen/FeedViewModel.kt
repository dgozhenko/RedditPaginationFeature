package com.example.redditpagination.ui.feed_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.redditpagination.network.response.RedditPost
import retrofit2.Retrofit
import javax.inject.Inject


class FeedViewModel @Inject constructor(private val retrofit: Retrofit): ViewModel() {
     private val _postLiveData = MutableLiveData<LiveData<PagedList<RedditPost>>>()
     val postLiveData: LiveData<LiveData<PagedList<RedditPost>>> get() = _postLiveData

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        _postLiveData.postValue(initializedPagedListBuilder(config).build())
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<String, RedditPost> {
        val dataSourceFactory = object: DataSource.Factory<String, RedditPost>() {
            override fun create(): DataSource<String, RedditPost> {
                return FeedDataSource(retrofit)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }
}