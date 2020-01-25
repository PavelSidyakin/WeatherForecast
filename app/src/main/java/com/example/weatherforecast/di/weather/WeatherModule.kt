package com.example.weatherforecast.di.weather

import com.example.weatherforecast.data.WeatherOfflineRepositoryImpl
import com.example.weatherforecast.data.WeatherOnlineRepositoryImpl
import com.example.weatherforecast.di.WeatherScope
import com.example.weatherforecast.domain.WeatherInteractor
import com.example.weatherforecast.domain.WeatherInteractorImpl
import com.example.weatherforecast.domain.WeatherOfflineRepository
import com.example.weatherforecast.domain.WeatherOnlineRepository
import dagger.Binds
import dagger.Module

@Module
abstract class WeatherModule {

    @WeatherScope
    @Binds
    abstract fun provideWeatherOnlineRepository(weatherOnlineRepository: WeatherOnlineRepositoryImpl): WeatherOnlineRepository

    @WeatherScope
    @Binds
    abstract fun provideWeatherOfflineRepository(weatherOfflineRepository: WeatherOfflineRepositoryImpl): WeatherOfflineRepository

    @WeatherScope
    @Binds
    abstract fun provideWeatherInteractor(weatherInteractor: WeatherInteractorImpl): WeatherInteractor

}