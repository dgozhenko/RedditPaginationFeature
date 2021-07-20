package com.example.redditpagination

import android.app.Application
import com.example.redditpagination.di.ApplicationComponent
import com.example.redditpagination.di.DaggerApplicationComponent
import com.example.redditpagination.di.module.AppModule

class App: Application() {

    val appComponent: ApplicationComponent =
        DaggerApplicationComponent.builder()
            .build()

}
