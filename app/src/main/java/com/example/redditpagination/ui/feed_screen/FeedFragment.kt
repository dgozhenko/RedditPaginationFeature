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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redditpagination.App
import com.example.redditpagination.databinding.FragmentFeedBinding
import javax.inject.Inject

class FeedFragment : Fragment() {

  // create binding with fragment
  private var _binding: FragmentFeedBinding? = null
  private val binding
    get() = _binding!!

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
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // create onClickListener logic
    val adapter =
        FeedAdapter(
            OnClickListener {
              val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.redirectUrl))
              startActivity(browserIntent)
            })

    // initialize recyclerView
    val recyclerView = binding.feedRv
    recyclerView.layoutManager = LinearLayoutManager(requireContext())
    recyclerView.adapter = adapter

    // observe for data changes in live data
    viewModel.postLiveData.observe(
        viewLifecycleOwner,
        {
          adapter.submitList(it)
        })
  }
}
