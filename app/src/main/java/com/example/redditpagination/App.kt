package com.example.redditpagination

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.redditpagination.di.ApplicationComponent
import com.example.redditpagination.di.DaggerApplicationComponent
import com.example.redditpagination.di.module.AppModule

@ExperimentalPagingApi
class App : Application() {

  // create dagger app component
  val appComponent: ApplicationComponent =
      DaggerApplicationComponent.builder().appModule(AppModule(this)).build()
}
