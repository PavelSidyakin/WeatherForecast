package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityWeatherOffline

interface WeatherOfflineRepository {

    suspend fun requestAvailableCities(): List<String>

    suspend fun requestCityWeatherForecast(cityName: String): CityWeatherOffline

    suspend fun saveForecast(citiesWeather: Map<String, CityWeatherOffline>)

}