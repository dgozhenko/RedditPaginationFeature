package com.example.redditpagination.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redditpagination.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView

// Adapter for header elements - error, progress, retry
class LoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingAdapter.LoadingStateViewHolder>() {

  // bind UI elements with data
  override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
    holder.bind(loadState)
  }

  // inflate layout
  override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingStateViewHolder {
    val layoutInflater =
      LayoutInflater.from(parent.context).inflate(R.layout.feed_adapter_loading, parent, false)
    return LoadingStateViewHolder(layoutInflater, retry)
  }

  class LoadingStateViewHolder(itemView: View, retry: () -> Unit) :
      RecyclerView.ViewHolder(itemView) {
    private val errorText = itemView.findViewById<MaterialTextView>(R.id.error)
    private val progressBar = itemView.findViewById<CircularProgressIndicator>(R.id.progress_bar)
    private val retryButton = itemView.findViewById<MaterialButton>(R.id.retry_button)

    // set on click retry
    init {
      retryButton.setOnClickListener { retry() }
    }

    // bind data
    fun bind(loadState: LoadState) {
      if (loadState is LoadState.Error) {
        errorText.text = loadState.error.localizedMessage
      }

      // visibility state
      progressBar.isVisible = loadState is LoadState.Loading
      errorText.isVisible = loadState !is LoadState.Loading
      retryButton.isVisible = loadState !is LoadState.Loading
    }
  }


}
