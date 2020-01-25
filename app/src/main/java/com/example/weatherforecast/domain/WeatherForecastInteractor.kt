package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.UpdateWeatherForecastResultCode
import com.example.weatherforecast.domain.model.WeatherOfflineForecast

interface WeatherForecastInteractor {

    suspend fun requestOfflineResult(): WeatherOfflineForecast

    suspend fun updateOfflineResult(): UpdateWeatherForecastResultCode

}