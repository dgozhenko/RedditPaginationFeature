package com.example.redditpagination.network.response

// listing
class RedditListing(val children: List<PostContainer>, val after: String?, val before: String?)
