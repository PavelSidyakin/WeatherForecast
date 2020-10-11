package com.example.weather_details.di

import com.example.weather_details.WeatherDetailsFragmentFactory
import com.example.weather_details.domain.WeatherDetailsInteractor
import com.example.weather_details.domain.WeatherDetailsInteractorImpl
import com.example.weather_details.presentation.city_weather.view.WeatherDetailsFragmentFactoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface WeatherDetailsModule {
    @Binds
    @Singleton
    fun provideWeatherDetailsFragmentFactory(weatherDetailsFragmentFactory: WeatherDetailsFragmentFactoryImpl): WeatherDetailsFragmentFactory

    @Binds
    @Singleton
    fun provideWeatherDetailsInteractor(weatherDetailsInteractor: WeatherDetailsInteractorImpl): WeatherDetailsInteractor
}