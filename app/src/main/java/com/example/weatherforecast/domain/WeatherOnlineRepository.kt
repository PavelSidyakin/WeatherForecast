package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestResult

interface WeatherOnlineRepository {

    /**
     * Requests weather from network.
     *
     * @return Request result [WeatherOnlineRequestResult]
     */
    suspend fun requestWeather(): WeatherOnlineRequestResult

}