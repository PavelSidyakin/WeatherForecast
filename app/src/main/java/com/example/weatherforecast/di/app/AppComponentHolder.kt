package com.example.weatherforecast.di.app

import com.example.common.logs.Logger
import com.example.module_injector.BaseAPI
import com.example.module_injector.BaseDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.data.GeneralSettingsRepositoryImpl
import com.example.weatherforecast.data.WeatherOfflineRepositoryImpl
import com.example.weatherforecast.data.WeatherOnlineRepositoryImpl

object AppComponentHolder : ComponentHolder<AppApi, AppDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            AppApi,
            AppDependencies,
            AppComponent> {
        AppComponent.initAndGet(it)
    }

    internal fun getComponent(): AppComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> AppDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): AppApi {
        return getComponent()
    }
}

interface AppApi : BaseAPI {
    val logger: Logger
    val generalSettingsRepositoryImpl: GeneralSettingsRepositoryImpl
    val weatherOnlineRepositoryImpl: WeatherOnlineRepositoryImpl
    val weatherOfflineRepositoryImpl: WeatherOfflineRepositoryImpl
}

interface AppDependencies : BaseDependencies {
    val appProvider: ApplicationProvider
}