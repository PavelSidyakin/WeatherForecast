package com.example.weatherforecast.domain.model

import java.util.Date

data class CityWeather(
    val dateToTemperatureMap: Map<Date, Float>
)