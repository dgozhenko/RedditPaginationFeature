package com.example.redditpagination.ui.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.redditpagination.network.data.ApiService
import com.example.redditpagination.network.response.RedditPost
import javax.inject.Inject
import retrofit2.Retrofit

// reddit feed source
class FeedSource @Inject constructor(retrofit: Retrofit) :
    PagingSource<String, RedditPost>() {
  // get api service
  private val apiService = retrofit.create(ApiService::class.java)

  // in this test there no need to overriding this function
  override fun getRefreshKey(state: PagingState<String, RedditPost>): String? {
    return null
  }

  override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditPost> {
    return try {
      // get data from service
      val response = apiService.fetchTopPosts(loadSize = params.loadSize)
      val listing = response.body()?.data
      val redditPost = listing?.children?.map { it.data }

      // load data as result
      LoadResult.Page(redditPost ?: listOf(), listing?.before, listing?.after)
    } catch (e: Exception) {
      // catch error
      LoadResult.Error(e)
    }
  }

   /*because of Reddit strange key API, I need to provide reuse of keys, you can try delete it, app
   will start, after slide it will crash, because pagingV3 for default can't reuse keys*/
  override val keyReuseSupported: Boolean = true
}
