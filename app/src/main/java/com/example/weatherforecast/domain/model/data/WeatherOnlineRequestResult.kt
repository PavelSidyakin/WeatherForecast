package com.example.weatherforecast.domain.model.data

data class WeatherOnlineRequestResult(
    val resultCode: WeatherOnlineRequestResultCode,
    val data: List<WeatherOnlineRequestDataItem>?
)