package com.example.redditpagination.ui.feed_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redditpagination.R
import com.example.redditpagination.network.response.RedditPost
import com.example.redditpagination.util.DiffUtilCallback

class FeedAdapter: PagedListAdapter<RedditPost, FeedAdapter.PostViewHolder>(DiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_adapter_row, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindPost(it)
        }
    }

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val scoreText = itemView.findViewById<TextView>(R.id.score)
        private val commentText = itemView.findViewById<TextView>(R.id.comments)
        private val titleText = itemView.findViewById<TextView>(R.id.title)

        fun bindPost(redditPost: RedditPost) {
            with(redditPost) {
                scoreText.text = score.toString()
                commentText.text = commentCount.toString()
                titleText.text = title
            }
        }
    }
}