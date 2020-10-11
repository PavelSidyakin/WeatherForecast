package com.example.city_list.models.data

data class WeatherOnlineRequestResult(
    val resultCode: WeatherOnlineRequestResultCode,
    val data: List<WeatherOnlineRequestDataItem>?
)