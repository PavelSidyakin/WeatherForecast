package com.example.city_list.models.data

import com.example.common.json_utils.GsonSerializable
import com.google.gson.annotations.SerializedName
import java.util.Date

// Todo: remove gson annotations (use clean model)
data class WeatherOnlineRequestDataItem(
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