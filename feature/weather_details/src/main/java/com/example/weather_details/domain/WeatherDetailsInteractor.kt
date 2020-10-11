package com.example.weather_details.domain

import com.example.common.models.CityInfo

internal interface WeatherDetailsInteractor {
    /**
     * Requests city weather.
     *
     * @param cityName city name
     * @return [CityInfo]
     */
    suspend fun requestCityInfo(cityName: String): CityInfo

    /**
     * Requests city weather.
     *
     * @param cityName city name
     * @return map time in millis to temperature in Celsius degrees
     */
    suspend fun requestCityWeather(cityName: String): Map<Long, Float>

}