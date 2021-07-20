package com.example.redditpagination.network.response

import com.google.gson.annotations.SerializedName

// post
data class RedditPost(
    @SerializedName("name") val key: String,
    @SerializedName("title") val title: String,
    @SerializedName("score") val score: Int,
    @SerializedName("author_fullname") val author: String,
    @SerializedName("num_comments") val commentCount: Int,
    @SerializedName("subreddit") val subreddit: String,
    @SerializedName("created") val created: Long,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("url") val redirectUrl: String
)
