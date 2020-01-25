package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.CityWeather
import java.util.Date

interface WeatherOfflineRepository {

    suspend fun requestAllCitiesInfo(): Map<String, CityInfo>

    suspend fun requestCityWeather(cityName: String): CityWeather

    suspend fun saveCitiesWeather(citiesWeather: Map<String, CityWeather>)

    suspend fun saveCitiesInfo(citiesWeather: Map<String, CityInfo>)

    suspend fun clearWeatherOlderThan(date: Date)

}