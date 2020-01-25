package com.example.weatherforecast.data

import com.example.weatherforecast.domain.WeatherOfflineRepository
import com.example.weatherforecast.domain.model.CityWeatherOffline
import javax.inject.Inject

class WeatherOfflineRepositoryImpl
    @Inject
    constructor() : WeatherOfflineRepository {

    override suspend fun requestAvailableCities(): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun requestCityWeatherForecast(cityName: String): CityWeatherOffline {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForecast(citiesWeather: Map<String, CityWeatherOffline>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}