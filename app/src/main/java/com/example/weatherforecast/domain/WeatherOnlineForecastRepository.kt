package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.data.WeatherOnlineForecastRequestResult

interface WeatherOnlineForecastRepository {

    suspend fun requestWeatherForecast(): WeatherOnlineForecastRequestResult

}