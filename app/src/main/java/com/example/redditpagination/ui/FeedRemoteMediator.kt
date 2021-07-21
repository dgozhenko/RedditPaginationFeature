package com.example.redditpagination.ui

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.redditpagination.database.RedditPaginationDatabase
import com.example.redditpagination.database.entity.RedditKeys
import com.example.redditpagination.network.data.ApiService
import com.example.redditpagination.network.response.RedditPost
import javax.inject.Inject
import retrofit2.Retrofit

@ExperimentalPagingApi
// remote mediator for saving and retrieving data to/from database
class FeedRemoteMediator
@Inject
constructor(retrofit: Retrofit, private val database: RedditPaginationDatabase) :
    RemoteMediator<Int, RedditPost>() {
  private val apiService: ApiService = retrofit.create(ApiService::class.java)
  override suspend fun load(
      loadType: LoadType,
      state: PagingState<Int, RedditPost>
  ): MediatorResult {
    return try {
      // instructions for different load types
      val loadKey =
          when (loadType) {
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.REFRESH -> null
            LoadType.APPEND -> {
              state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
              getKeys()
            }
          }
      // get data from reddit API
      val response =
          apiService.fetchTopPosts(
              loadSize = state.config.pageSize, after = loadKey?.after, before = loadKey?.before)
      val listing = response.body()?.data
      val redditPost = listing?.children?.map { it.data }

      if (redditPost != null) {
        // save data to database
        database.withTransaction {
          database.redditKeysDao().saveRedditKeys(RedditKeys(0, listing.after, listing.before))
          database.redditPostsDao().savePosts(redditPost)
        }
      }
      MediatorResult.Success(endOfPaginationReached = listing?.after == null)
    } catch (e: Exception) {
      // catch error
      MediatorResult.Error(e)
    }
  }

  // get keys from database
  private suspend fun getKeys(): RedditKeys? {
    return database.redditKeysDao().getRedditKeys().firstOrNull()
  }
}
