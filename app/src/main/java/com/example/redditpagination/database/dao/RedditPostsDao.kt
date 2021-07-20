package com.example.redditpagination.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redditpagination.network.response.RedditPost

// Data access object for reddit posts
@Dao
interface RedditPostsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun savePosts(posts: List<RedditPost>)

  @Query("SELECT * FROM redditPosts") fun getPosts(): PagingSource<Int, RedditPost>
}
