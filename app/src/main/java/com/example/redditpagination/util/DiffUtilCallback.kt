package com.example.redditpagination.util

import androidx.recyclerview.widget.DiffUtil
import com.example.redditpagination.network.response.RedditPost

// diff util for proper items swap
class DiffUtilCallback : DiffUtil.ItemCallback<RedditPost>() {
  override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
    return oldItem.key == newItem.key
  }

  override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
    return oldItem.title == newItem.title &&
        oldItem.score == newItem.score &&
        oldItem.commentCount == newItem.commentCount
  }
}
