package com.example.weatherforecast.di.app

import com.example.common.logs.Logger
import com.example.weatherforecast.common.ApplicationProviderImpl
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.common.LoggerImpl
import com.example.weatherforecast.data.GeneralSettingsRepositoryImpl
import com.example.weatherforecast.data.WeatherOfflineRepositoryImpl
import com.example.weatherforecast.data.WeatherOnlineRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideLogger(logger: LoggerImpl): Logger

}