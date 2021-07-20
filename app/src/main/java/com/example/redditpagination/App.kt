package com.example.redditpagination

import android.app.Application
import com.example.redditpagination.di.ApplicationComponent
import com.example.redditpagination.di.DaggerApplicationComponent

class App : Application() {

  // create dagger app component
  val appComponent: ApplicationComponent = DaggerApplicationComponent.builder().build()
}
