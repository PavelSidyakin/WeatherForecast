package com.example.weather_details

import com.example.weather_details.presentation.city_weather.view.WeatherDetailsFragment

interface WeatherDetailsFragmentFactory {
    fun createWeatherDetailsFragment(cityName: String): WeatherDetailsFragment
}