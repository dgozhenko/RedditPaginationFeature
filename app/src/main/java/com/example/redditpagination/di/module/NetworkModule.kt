package com.example.redditpagination.di.module

import com.example.redditpagination.network.data.ApiEndPoint.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

  companion object {
    private val loggingInterceptor =
        HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
  }

  @Singleton
  @Provides
  fun providesHttpClient(): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor)
        .build()
  }

  // method that provides gson
  @Singleton
  @Provides
  fun providesGson(): Gson {
    return GsonBuilder().setLenient().create()
  }

  // method that create retrofit
  @Singleton
  @Provides
  fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(providesGson()))
        .build()
  }
}
