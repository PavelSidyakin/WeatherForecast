package com.example.weatherforecast.presentation.city_weather.view

import com.example.weatherforecast.presentation.city_weather.WeatherListItemData
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface WeatherView: MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCityHeader(name: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCityPicture(pictureUrl: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showWeatherList(cityWeather: List<WeatherListItemData>)

}