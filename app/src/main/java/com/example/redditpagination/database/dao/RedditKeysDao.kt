package com.example.redditpagination.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redditpagination.database.entity.RedditKeys

// Data access object for reddit keys
@Dao
interface RedditKeysDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun saveRedditKeys(redditKey: RedditKeys)

  @Query("SELECT * FROM redditKeys ORDER BY id DESC") suspend fun getRedditKeys(): List<RedditKeys>
}
