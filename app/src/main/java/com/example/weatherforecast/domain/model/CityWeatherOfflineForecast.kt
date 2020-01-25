package com.example.weatherforecast.domain.model

import java.util.Date

data class CityWeatherOfflineForecast(
    val city: City,
    val timeToTemperatureMap: Map<Date, Float>
) {

    data class City(
        val name: String,
        val pictureBytes: ByteArray
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as City

            if (name != other.name) return false
            if (!pictureBytes.contentEquals(other.pictureBytes)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + pictureBytes.contentHashCode()
            return result
        }
    }
}