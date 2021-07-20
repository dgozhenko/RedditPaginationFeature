package com.example.redditpagination.ui.feed_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.redditpagination.database.RedditPaginationDatabase
import com.example.redditpagination.network.response.RedditPost
import com.example.redditpagination.ui.repository.FeedRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit

@ExperimentalPagingApi
class FeedViewModel
@Inject
constructor(retrofit: Retrofit, database: RedditPaginationDatabase) :
    ViewModel() {
  // get instance of feed repo
  private val feedRepository = FeedRepository(retrofit, database)

  // get data from repo
  fun getFeed(): Flow<PagingData<RedditPost>> {
    return feedRepository.fetchPosts().cachedIn(viewModelScope)
  }
}
