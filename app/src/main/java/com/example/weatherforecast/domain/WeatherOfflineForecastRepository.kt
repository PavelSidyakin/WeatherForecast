package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityWeatherOfflineForecast

interface WeatherOfflineForecastRepository {

    suspend fun requestAvailableCities(): List<String>

    suspend fun requestCityWeatherForecast(cityName: String): CityWeatherOfflineForecast

    suspend fun saveForecast(citiesForecast: Map<String, CityWeatherOfflineForecast>)

}