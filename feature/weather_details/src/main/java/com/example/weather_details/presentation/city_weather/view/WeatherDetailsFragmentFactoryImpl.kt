package com.example.weather_details.presentation.city_weather.view

import androidx.fragment.app.Fragment
import com.example.weather_details.WeatherDetailsFragmentFactory
import javax.inject.Inject

class WeatherDetailsFragmentFactoryImpl @Inject constructor(

) : WeatherDetailsFragmentFactory {
    override fun createWeatherDetailsFragment(cityName: String): Fragment {
        return WeatherDetailsFragment(cityName)
    }
}