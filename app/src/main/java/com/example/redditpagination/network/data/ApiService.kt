package com.example.redditpagination.network.data

import com.example.redditpagination.network.response.RedditApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/top/.json")
    suspend fun fetchTopPosts(
        @Query("limit") loadSize: Int = 10,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Response<RedditApiResponse>
}