package com.example.weather_details.domain

import com.example.common.models.CityInfo
import javax.inject.Inject

class WeatherDetailsInteractorImpl @Inject constructor(
    private val weatherDetailsRepository: WeatherDetailsRepository,
) : WeatherDetailsInteractor {

    override suspend fun requestCityWeather(cityName: String): Map<Long, Float> {
        return weatherDetailsRepository.requestCityWeather(cityName)
    }

    override suspend fun requestCityInfo(cityName: String): CityInfo {
        return weatherDetailsRepository.requestCityInfo(cityName)
    }
}