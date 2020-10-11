package com.example.city_list.di

import com.example.city_list.CityListFragmentFactory
import com.example.city_list.CityListMonitor
import com.example.city_list.domain.CityListObserverImpl
import com.example.city_list.domain.CityListUpdater
import com.example.city_list.domain.WeatherInteractor
import com.example.city_list.domain.WeatherInteractorImpl
import com.example.city_list.presentation.city_list.presenter.CityListPresenter
import com.example.city_list.presentation.city_list.view.CityListFragmentFactoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface CityListModule {
    @Binds
    @Singleton
    fun provideWeatherInteractor(weatherInteractor: WeatherInteractorImpl): WeatherInteractor

    @Binds
    @Singleton
    fun provideCityListUpdater(cityListObserver: CityListObserverImpl): CityListUpdater

    @Binds
    @Singleton
    fun provideCityListMonitor(cityListObserver: CityListObserverImpl): CityListMonitor

    @Binds
    @Singleton
    fun provideCityListFragmentFactory(cityListFragmentFactory: CityListFragmentFactoryImpl): CityListFragmentFactory

}