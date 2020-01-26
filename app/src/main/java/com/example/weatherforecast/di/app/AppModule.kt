package com.example.weatherforecast.di.app

import com.example.weatherforecast.common.ApplicationProviderImpl
import com.example.weatherforecast.common.ApplicationProvider
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

}