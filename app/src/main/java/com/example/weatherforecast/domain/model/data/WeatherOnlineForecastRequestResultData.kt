package com.example.weatherforecast.domain.model.data

import com.example.weatherforecast.common.GsonSerializable
import com.google.gson.annotations.SerializedName
import java.util.Date

data class WeatherOnlineForecastRequestResultData(
    val date: Date,
    val city: City,
    val tempType: TemperatureType,
    val temp: Float
) : GsonSerializable {

    data class City(
        val name: String,
        val picture: String
    ) : GsonSerializable

    enum class TemperatureType {
        @SerializedName("K")
        KELVIN,

        @SerializedName("C")
        CELSIUS,

        @SerializedName("F")
        FAHRENHEIT,
    }
}