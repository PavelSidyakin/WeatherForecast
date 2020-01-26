package com.example.weatherforecast.presentation.city_weather.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface WeatherView: MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showWeather()

}