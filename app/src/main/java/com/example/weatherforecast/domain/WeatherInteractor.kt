package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.UpdateWeatherResultCode
import java.util.Date

interface WeatherInteractor {

    suspend fun requestAllCitiesInfo(): Map<String, CityInfo>

    suspend fun requestCityWeather(cityName: String): Map<Date, Float>

    suspend fun updateOfflineResult(): UpdateWeatherResultCode

}