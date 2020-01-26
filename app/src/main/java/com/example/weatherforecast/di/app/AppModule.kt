package com.example.weatherforecast.di.app

import com.example.weatherforecast.common.ApplicationProviderImpl
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.data.GeneralSettingsRepositoryImpl
import com.example.weatherforecast.data.TimeProviderImpl
import com.example.weatherforecast.domain.GeneralSettingsRepository
import com.example.weatherforecast.domain.TimeProvider
import com.example.weatherforecast.utils.DispatcherProvider
import com.example.weatherforecast.utils.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideApplicationProvider(applicationProvider: ApplicationProviderImpl): ApplicationProvider

    @Singleton
    @Binds
    abstract fun provideDispatcherProvider(dispatcherProvider: DispatcherProviderImpl): DispatcherProvider

    @Singleton
    @Binds
    abstract fun provideGeneralSettingsRepository(generalSettingsRepository: GeneralSettingsRepositoryImpl): GeneralSettingsRepository


    @Singleton
    @Binds
    abstract fun provideTimeProvider(timeProvider: TimeProviderImpl): TimeProvider

}