package com.example.weather_details

import androidx.fragment.app.Fragment

interface WeatherDetailsFragmentFactory {
    fun createWeatherDetailsFragment(cityName: String): Fragment
}