package com.example.weather_details.di

import com.example.weather_details.WeatherDetailsApi
import com.example.weather_details.WeatherDetailsDependencies
import com.example.weather_details.presentation.city_weather.presenter.WeatherDetailsPresenter
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [WeatherDetailsDependencies::class], modules = [WeatherDetailsModule::class, WeatherDetailsAssistedModule::class])
@Singleton
internal interface WeatherDetailsComponent : WeatherDetailsApi {

    val weatherDetailsPresenterFactory: WeatherDetailsPresenter.Factory

    companion object {
        fun initAndGet(weatherDetailsDependencies: WeatherDetailsDependencies): WeatherDetailsComponent {
            return DaggerWeatherDetailsComponent.builder()
                .weatherDetailsDependencies(weatherDetailsDependencies)
                .build()
        }
    }
}
