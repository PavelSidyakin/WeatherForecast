package com.example.weather_details.presentation.city_weather.view

import com.example.weather_details.WeatherDetailsFragmentFactory
import javax.inject.Inject

internal class WeatherDetailsFragmentFactoryImpl @Inject constructor(

) : WeatherDetailsFragmentFactory {
    override fun createWeatherDetailsFragment(cityName: String): WeatherDetailsFragment {
        return WeatherDetailsFragment(cityName)
    }
}