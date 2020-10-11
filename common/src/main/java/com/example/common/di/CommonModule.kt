package com.example.common.di

import com.example.common.coroutine_utils.DispatcherProvider
import com.example.common.coroutine_utils.DispatcherProviderImpl
import com.example.common.time_utils.TimeProvider
import com.example.common.time_utils.TimeProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class CommonModule {
    @Singleton
    @Binds
    abstract fun provideDispatcherProvider(dispatcherProvider: DispatcherProviderImpl): DispatcherProvider

    @Singleton
    @Binds
    abstract fun provideTimeProvider(timeProvider: TimeProviderImpl): TimeProvider

}