package com.example.common.models

import java.util.Date

data class CityWeather(
    /**
     * Date to temperature map.
     *
     * Temperature is in Celsius degrees
     */
    val dateToTemperatureMap: Map<Date, Float>
)