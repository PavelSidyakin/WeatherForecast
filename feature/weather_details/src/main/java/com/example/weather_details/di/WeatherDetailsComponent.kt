package com.example.weather_details.di

import com.example.weather_details.WeatherDetailsFeatureApi
import com.example.weather_details.WeatherDetailsFeatureDependencies
import com.example.weather_details.presentation.city_weather.presenter.WeatherDetailsPresenter
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [WeatherDetailsFeatureDependencies::class], modules = [WeatherDetailsModule::class, WeatherDetailsAssistedModule::class])
@Singleton
internal interface WeatherDetailsComponent : WeatherDetailsFeatureApi {

    val weatherDetailsPresenterFactory: WeatherDetailsPresenter.Factory

    companion object {
        fun initAndGet(weatherDetailsDependencies: WeatherDetailsFeatureDependencies): WeatherDetailsComponent {
            return DaggerWeatherDetailsComponent.builder()
                .weatherDetailsFeatureDependencies(weatherDetailsDependencies)
                .build()
        }
    }
}
