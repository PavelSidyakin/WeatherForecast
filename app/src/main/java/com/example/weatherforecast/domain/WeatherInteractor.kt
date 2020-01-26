package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.UpdateOfflineResultCode

interface WeatherInteractor {

    suspend fun requestAllCitiesInfo(): Map<String, CityInfo>

    suspend fun requestCityInfo(cityName: String): CityInfo

    suspend fun requestCityWeather(cityName: String): Map<Long, Float>

    suspend fun updateAllOfflineInfo(): UpdateOfflineResultCode

    fun getLastUpdateTime(): Long

}