package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.UpdateOfflineResultCode
import java.util.Date

interface WeatherInteractor {

    suspend fun requestAllCitiesInfo(): Map<String, CityInfo>

    suspend fun requestCityWeather(cityName: String): Map<Date, Float>

    suspend fun updateOfflineWeather(): UpdateOfflineResultCode

    suspend fun updateOfflineCityInfo(): UpdateOfflineResultCode

}