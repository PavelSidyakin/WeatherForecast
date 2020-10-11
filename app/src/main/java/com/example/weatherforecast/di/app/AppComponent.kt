package com.example.weatherforecast.di.app

import com.example.weatherforecast.TheApplication
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(theApplication: TheApplication)

    interface Builder {
        fun build(): AppComponent
    }
}