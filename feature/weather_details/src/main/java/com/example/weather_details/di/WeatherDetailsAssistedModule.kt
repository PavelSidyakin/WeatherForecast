package com.example.weather_details.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_WeatherDetailsAssistedModule::class])
internal interface WeatherDetailsAssistedModule
