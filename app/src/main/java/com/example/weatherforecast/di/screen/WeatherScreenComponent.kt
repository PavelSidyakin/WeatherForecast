package com.example.weatherforecast.di.screen

import com.example.weatherforecast.presentation.MainActivity
import com.example.weatherforecast.di.WeatherScope
import com.example.weatherforecast.di.weather.WeatherModule
import com.example.weatherforecast.presentation.city_list.presenter.CityListPresenter
import com.example.weatherforecast.presentation.city_weather.presenter.WeatherPresenter
import dagger.Subcomponent

@Subcomponent(modules = [WeatherModule::class])
@WeatherScope
interface WeatherScreenComponent {
    fun inject(mainActivity: MainActivity)

    fun getCityListPresenter(): CityListPresenter

    fun getWeatherPresenter(): WeatherPresenter

    interface Builder {
        fun build(): WeatherScreenComponent
    }
}