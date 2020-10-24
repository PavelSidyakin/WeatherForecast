package com.example.weather_details.presentation.city_weather.view

import com.example.weather_details.presentation.city_weather.WeatherListItemData
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

internal interface WeatherView: MvpView {

    @AddToEndSingle
    fun showCityHeader(name: String)

    @AddToEndSingle
    fun showCityPicture(pictureUrl: String)

    @AddToEndSingle
    fun showWeatherList(cityWeather: List<WeatherListItemData>)

}