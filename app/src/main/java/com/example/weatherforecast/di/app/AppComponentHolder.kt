package com.example.weatherforecast.di.app

import com.example.common.logs.Logger
import com.example.module_injector.BaseFeatureAPI
import com.example.module_injector.BaseFeatureDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.data.GeneralSettingsRepositoryImpl
import com.example.weatherforecast.data.WeatherOfflineRepositoryImpl
import com.example.weatherforecast.data.WeatherOnlineRepositoryImpl

object AppComponentHolder : ComponentHolder<AppFeatureApi, AppFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            AppFeatureApi,
            AppFeatureDependencies,
            AppComponent> {
        AppComponent.initAndGet(it)
    }

    internal fun getComponent(): AppComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> AppFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): AppFeatureApi {
        return getComponent()
    }
}

interface AppFeatureApi : BaseFeatureAPI {
    val logger: Logger
    val generalSettingsRepositoryImpl: GeneralSettingsRepositoryImpl
    val weatherOnlineRepositoryImpl: WeatherOnlineRepositoryImpl
    val weatherOfflineRepositoryImpl: WeatherOfflineRepositoryImpl
}

interface AppFeatureDependencies : BaseFeatureDependencies {
    val appProvider: ApplicationProvider
}