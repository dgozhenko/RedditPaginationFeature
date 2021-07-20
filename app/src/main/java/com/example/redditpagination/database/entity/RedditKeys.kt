package com.example.redditpagination.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// database entity
@Entity(tableName = "redditKeys")
data class RedditKeys(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val after: String?,
    val before: String?
)
