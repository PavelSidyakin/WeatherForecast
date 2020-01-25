package com.example.weatherforecast.di.screen

import com.example.weatherforecast.MainActivity
import com.example.weatherforecast.di.WeatherScope
import com.example.weatherforecast.di.weather.WeatherModule
import dagger.Subcomponent

@Subcomponent(modules = [WeatherModule::class])
@WeatherScope
interface WeatherScreenComponent {
    fun inject(mainActivity: MainActivity)
}