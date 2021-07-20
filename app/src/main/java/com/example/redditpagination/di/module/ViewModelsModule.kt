package com.example.redditpagination.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.redditpagination.di.ViewModelKey
import com.example.redditpagination.di.ViewModelsFactory
import com.example.redditpagination.ui.feed_screen.FeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

  // method that bind view model factory, so view models can call DI for injection
  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelsFactory): ViewModelProvider.Factory

  // method that binds auth view model with DI
  @Binds
  @IntoMap
  @ViewModelKey(FeedViewModel::class)
  abstract fun bindsFeedViewModel(viewModel: FeedViewModel?): ViewModel?
}
