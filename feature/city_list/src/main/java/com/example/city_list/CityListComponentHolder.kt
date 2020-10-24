package com.example.city_list

import com.example.city_list.di.CityListComponent
import com.example.city_list.domain.GeneralSettingsRepository
import com.example.city_list.domain.WeatherOfflineRepository
import com.example.city_list.domain.WeatherOnlineRepository
import com.example.common.coroutine_utils.DispatcherProvider
import com.example.common.time_utils.TimeProvider
import com.example.module_injector.BaseFeatureAPI
import com.example.module_injector.BaseFeatureDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate

object CityListComponentHolder : ComponentHolder<CityListFeatureApi, CityListFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            CityListFeatureApi,
            CityListFeatureDependencies,
            CityListComponent> { dependencies: CityListFeatureDependencies ->
        CityListComponent.initAndGet(dependencies)
    }

    internal fun getComponent(): CityListComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> CityListFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): CityListFeatureApi = componentHolderDelegate.get()
}

interface CityListFeatureDependencies : BaseFeatureDependencies {
    val dispatcherProvider: DispatcherProvider
    val timeProvider: TimeProvider

    val generalSettingsRepository: GeneralSettingsRepository

    val weatherOfflineRepository: WeatherOfflineRepository
    val weatherOnlineRepository: WeatherOnlineRepository
}

interface CityListFeatureApi : BaseFeatureAPI {
    val cityListFragmentFactory: CityListFragmentFactory
    val cityListMonitor: CityListMonitor
}

