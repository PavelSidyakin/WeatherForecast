package com.example.city_list.domain

import com.example.city_list.models.data.WeatherOnlineRequestResult

interface WeatherOnlineRepository {

    /**
     * Requests weather from network.
     *
     * @return Request result [WeatherOnlineRequestResult]
     */
    suspend fun requestWeather(): WeatherOnlineRequestResult

}