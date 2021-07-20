package com.example.redditpagination.di

import com.example.redditpagination.di.module.AppModule
import com.example.redditpagination.di.module.NetworkModule
import com.example.redditpagination.di.module.ViewModelsModule
import com.example.redditpagination.ui.feed_screen.FeedFragment
import dagger.Component
import javax.inject.Singleton

// dagger application component
@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelsModule::class])
interface ApplicationComponent {
  // inject dagger in feed fragment
  fun inject(fragment: FeedFragment)
}
