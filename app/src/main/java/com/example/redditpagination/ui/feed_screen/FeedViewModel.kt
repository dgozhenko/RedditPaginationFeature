package com.example.redditpagination.ui.feed_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.redditpagination.network.response.RedditPost
import javax.inject.Inject
import retrofit2.Retrofit

class FeedViewModel @Inject constructor(private val retrofit: Retrofit) : ViewModel() {
  // live data with pagedList of RedditPost
  private var _postLiveData: LiveData<PagedList<RedditPost>>
  val postLiveData: LiveData<PagedList<RedditPost>>
    get() = _postLiveData

  init {
    // create configuration
    val config = PagedList.Config.Builder().setPageSize(50).setEnablePlaceholders(false).build()

    // swap empty live data with live data from paging library
    _postLiveData = initializedPagedListBuilder(config).build()
  }

  // function that call data from data source
  private fun initializedPagedListBuilder(
      config: PagedList.Config
  ): LivePagedListBuilder<String, RedditPost> {
    val dataSourceFactory =
        object : DataSource.Factory<String, RedditPost>() {
          override fun create(): DataSource<String, RedditPost> {
            return FeedDataSource(retrofit)
          }
        }
    return LivePagedListBuilder(dataSourceFactory, config)
  }
}
