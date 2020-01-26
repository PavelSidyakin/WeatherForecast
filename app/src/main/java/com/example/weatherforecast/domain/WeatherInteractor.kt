package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.UpdateOfflineResultCode

interface WeatherInteractor {

    /**
     * Requests all cities info in offline storage.
     *
     * @return map city name to [CityInfo]
     */
    suspend fun requestAllCitiesInfo(): Map<String, CityInfo>

    /**
     * Requests city info in offline storage.
     *
     * @param cityName city name
     * @return [CityInfo]
     */
    suspend fun requestCityInfo(cityName: String): CityInfo

    /**
     * Requests city weather in offline storage.
     *
     * @param cityName city name
     * @return map time in millis to temperature in Celsius degrees
     */
    suspend fun requestCityWeather(cityName: String): Map<Long, Float>

    /**
     * Updates offline storage.
     * If returns [UpdateOfflineResultCode.OK], then [getLastUpdateTime] will return new time.
     *
     * @return [UpdateOfflineResultCode]
     */
    suspend fun updateAllOfflineInfo(): UpdateOfflineResultCode

    /**
     * Returns last update offline storage time.
     *
     * @return time in milliseconds
     */
    fun getLastUpdateTime(): Long

}