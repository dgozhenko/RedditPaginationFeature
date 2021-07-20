package com.example.redditpagination.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.redditpagination.database.dao.RedditKeysDao
import com.example.redditpagination.database.dao.RedditPostsDao
import com.example.redditpagination.database.entity.RedditKeys
import com.example.redditpagination.network.response.RedditPost

// Room Database
@Database(entities = [RedditPost::class, RedditKeys::class], version = 1, exportSchema = false)
abstract class RedditPaginationDatabase : RoomDatabase() {
  abstract fun redditPostsDao(): RedditPostsDao
  abstract fun redditKeysDao(): RedditKeysDao
}
