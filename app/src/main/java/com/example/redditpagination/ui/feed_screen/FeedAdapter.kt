package com.example.redditpagination.ui.feed_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redditpagination.R
import com.example.redditpagination.network.response.RedditPost
import com.example.redditpagination.util.DiffUtilCallback
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.android.material.textview.MaterialTextView

class FeedAdapter : PagedListAdapter<RedditPost, FeedAdapter.PostViewHolder>(DiffUtilCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_adapter_row, parent, false)
    return PostViewHolder(view)
  }

  override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
    getItem(position)?.let { holder.bindPost(it) }
  }

  class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val scoreText = itemView.findViewById<MaterialTextView>(R.id.score)
    private val commentText = itemView.findViewById<MaterialTextView>(R.id.comments)
    private val titleText = itemView.findViewById<MaterialTextView>(R.id.title)
    private val authorText = itemView.findViewById<MaterialTextView>(R.id.author)
    private val subredditText = itemView.findViewById<MaterialTextView>(R.id.subreddit)
    private val postDateText = itemView.findViewById<MaterialTextView>(R.id.post_creation_date)
    private val thumbnailImage = itemView.findViewById<ImageView>(R.id.thumbnail)

    fun bindPost(redditPost: RedditPost) {
      with(redditPost) {
        val textInAgoFormat = TimeAgo.using(created)
        scoreText.text = score.toString()
        commentText.text = commentCount.toString()
        titleText.text = title
        authorText.text = author
        subredditText.text = subreddit
        postDateText.text = textInAgoFormat
        Glide.with(thumbnailImage)
            .load(thumbnail)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .circleCrop()
            .into(thumbnailImage)
      }
    }
  }
}
