package com.example.redditpagination.di.module

import android.content.Context
import com.example.redditpagination.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Create App Module of dagger
@Module
class AppModule(private val app: App) {

  // method that provides context to DI
  @Singleton @Provides fun providesContext(): Context = app.baseContext
}
