package com.example.redditpagination.di.module

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.example.redditpagination.App
import com.example.redditpagination.database.RedditPaginationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Create App Module of dagger
@Module
class AppModule @ExperimentalPagingApi constructor(private val app: App) {

  // method that provides context to DI
  @Singleton @Provides fun providesContext(): Context = app.baseContext
  // method that provides room database
  @Singleton
  @Provides
  fun provideRoomDatabase(context: Context): RedditPaginationDatabase {
    return Room.databaseBuilder(
            context, RedditPaginationDatabase::class.java, "reddit_pagination_database")
        .fallbackToDestructiveMigration()
        .build()
  }
}
