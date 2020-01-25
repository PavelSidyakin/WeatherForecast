package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestResult

interface WeatherOnlineRepository {

    suspend fun requestWeatherForecast(): WeatherOnlineRequestResult

}