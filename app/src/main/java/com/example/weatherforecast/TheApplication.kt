package com.example.weatherforecast

import android.app.Application
import com.example.weatherforecast.di.app.AppComponent
import com.example.weatherforecast.di.app.DaggerAppComponent
import com.example.weatherforecast.common.ApplicationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


class TheApplication : Application() {
    @Inject
    lateinit var applicationProvider: ApplicationProvider

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .build()

        appComponent.inject(this)

        applicationProvider.init(this)

    }

    companion object {
        private const val TAG = "TheApplication"

        private lateinit var appComponent: AppComponent

        fun getAppComponent(): AppComponent {
            return appComponent
        }
    }
}