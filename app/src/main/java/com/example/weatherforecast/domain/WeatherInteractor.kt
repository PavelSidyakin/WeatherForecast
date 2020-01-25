package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.UpdateWeatherResultCode
import com.example.weatherforecast.domain.model.WeatherOffline

interface WeatherInteractor {

    suspend fun requestOfflineResult(): WeatherOffline

    suspend fun updateOfflineResult(): UpdateWeatherResultCode

}