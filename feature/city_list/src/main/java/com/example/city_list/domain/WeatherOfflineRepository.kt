package com.example.city_list.domain

import com.example.city_list.models.data.WeatherOfflineSaveResultCode
import com.example.common.models.CityInfo
import com.example.common.models.CityWeather

interface WeatherOfflineRepository {

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
     * Saves cities weather in offline storage.
     *
     * @param citiesWeather map city name to [CityWeather]
     * @return Save result code
     */
    suspend fun saveCitiesWeather(citiesWeather: Map<String, CityWeather>): WeatherOfflineSaveResultCode

    /**
     * Saves cities info in offline storage.
     *
     * @param citiesInfo map city name to [CityInfo]
     * @return Save result code
     */
    suspend fun saveCitiesInfo(citiesInfo: Map<String, CityInfo>): WeatherOfflineSaveResultCode

    /**
     * Clears old weather data older than specified time.
     *
     * @param time time in millis
     */
    suspend fun clearWeatherOlderThan(time: Long)

    /**
     * Clears all cities weather data
     */
    suspend fun clearCitiesWeather()

    /**
     * Clears all cities info data
     */
    suspend fun clearCitiesInfo()

    /**
     * Clears all data (cities info and cities weather)
     */
    suspend fun clearAllData()

}