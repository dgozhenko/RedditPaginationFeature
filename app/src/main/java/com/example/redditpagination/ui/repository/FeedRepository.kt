package com.example.redditpagination.ui.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.redditpagination.database.RedditPaginationDatabase
import com.example.redditpagination.network.response.RedditPost
import com.example.redditpagination.ui.FeedRemoteMediator
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit

@ExperimentalPagingApi
class FeedRepository
@Inject
constructor(private val retrofit: Retrofit, private val database: RedditPaginationDatabase) {

  // fetch posts using flow from database, 10 per page
  fun fetchPosts(): Flow<PagingData<RedditPost>> {
    return Pager(
            PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = FeedRemoteMediator(retrofit, database),
            pagingSourceFactory = { database.redditPostsDao().getPosts() })
        .flow
  }
}
