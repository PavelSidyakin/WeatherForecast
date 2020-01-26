package com.example.weatherforecast.di.screen

import com.example.weatherforecast.presentation.MainActivity
import com.example.weatherforecast.di.WeatherScope
import com.example.weatherforecast.di.app.AppComponent
import com.example.weatherforecast.di.weather.WeatherModule
import com.example.weatherforecast.presentation.city_list.presenter.CityListPresenter
import dagger.Subcomponent

@Subcomponent(modules = [WeatherModule::class])
@WeatherScope
interface WeatherScreenComponent {
    fun inject(mainActivity: MainActivity)

    fun getCityListPresenter(): CityListPresenter

    interface Builder {
        fun build(): WeatherScreenComponent
    }
}