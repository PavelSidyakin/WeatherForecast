package com.example.weatherforecast.di.app

import com.example.weatherforecast.TheApplication
import com.example.weatherforecast.di.screen.WeatherScreenComponent
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(theApplication: TheApplication)

    fun getWeatherScreenComponent(): WeatherScreenComponent

    interface Builder {
        fun build(): AppComponent
    }
}