package com.example.weatherforecast.domain.model.data

data class WeatherOnlineForecastRequestResult(
    val resultCode: WeatherOnlineForecastRequestResultCode,
    val data: WeatherOnlineForecastRequestResultData?
)