package com.example.city_list

import com.example.city_list.di.CityListComponent
import com.example.city_list.domain.GeneralSettingsRepository
import com.example.city_list.domain.WeatherOfflineRepository
import com.example.city_list.domain.WeatherOnlineRepository
import com.example.common.coroutine_utils.DispatcherProvider
import com.example.common.time_utils.TimeProvider
import com.example.module_injector.BaseAPI
import com.example.module_injector.BaseDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate

object CityListComponentHolder : ComponentHolder<CityListApi, CityListDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            CityListApi,
            CityListDependencies,
            CityListComponent> { dependencies: CityListDependencies ->
        CityListComponent.initAndGet(dependencies)
    }

    internal fun getComponent(): CityListComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> CityListDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): CityListApi = componentHolderDelegate.get()
}

interface CityListDependencies : BaseDependencies {
    val dispatcherProvider: DispatcherProvider
    val timeProvider: TimeProvider

    val generalSettingsRepository: GeneralSettingsRepository

    val weatherOfflineRepository: WeatherOfflineRepository
    val weatherOnlineRepository: WeatherOnlineRepository
}

interface CityListApi : BaseAPI {
    val cityListFragmentFactory: CityListFragmentFactory
    val cityListMonitor: CityListMonitor
}

