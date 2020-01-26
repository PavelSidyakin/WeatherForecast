package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.CityWeather
import com.example.weatherforecast.domain.model.data.WeatherOfflineSaveResultCode

interface WeatherOfflineRepository {

    suspend fun requestAllCitiesInfo(): Map<String, CityInfo>

    suspend fun requestCityInfo(cityName: String): CityInfo

    suspend fun requestCityWeather(cityName: String): Map<Long, Float>

    suspend fun saveCitiesWeather(citiesWeather: Map<String, CityWeather>): WeatherOfflineSaveResultCode

    suspend fun saveCitiesInfo(citiesInfo: Map<String, CityInfo>): WeatherOfflineSaveResultCode

    suspend fun clearWeatherOlderThan(time: Long)

    suspend fun clearCitiesWeather()

    suspend fun clearCitiesInfo()

    suspend fun clearAllData()



}