package com.example.redditpagination.ui.feed_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redditpagination.App
import com.example.redditpagination.databinding.FragmentFeedBinding
import com.example.redditpagination.ui.adapter.FeedAdapter
import com.example.redditpagination.ui.adapter.LoadingAdapter
import com.example.redditpagination.ui.adapter.OnClickListener
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class FeedFragment : Fragment() {

  // create binding with fragment
  private var _binding: FragmentFeedBinding? = null
  private val binding
    get() = _binding!!

  // create adapter for access in whole fragment
  private var _adapter: FeedAdapter? = null
  private val adapter
    get() = _adapter!!

  // inject viewModelFactory and viewModel
  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel by viewModels<FeedViewModel> { viewModelFactory }

  // inject app component
  override fun onAttach(context: Context) {
    super.onAttach(context)
    (requireActivity().application as App).appComponent.inject(this)
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    // initialize binding
    _binding = FragmentFeedBinding.inflate(inflater)

    // initialize adapter and on click listener
    _adapter =
        FeedAdapter(
            OnClickListener {
              val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.redirectUrl))
              startActivity(browserIntent)
            })
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val recyclerView = binding.feedRv
    recyclerView.layoutManager = LinearLayoutManager(requireContext())
    // add header and footer
    recyclerView.adapter =
        adapter.withLoadStateHeaderAndFooter(
            header = LoadingAdapter { adapter.retry() },
            footer = LoadingAdapter { adapter.retry() })
    // call getPosts function
    getPosts()
  }

  // get data in scope from viewModel and submit it to recycler view
  private fun getPosts() {
    lifecycleScope.launch { viewModel.getFeed().collectLatest { adapter.submitData(it) } }
  }
}
