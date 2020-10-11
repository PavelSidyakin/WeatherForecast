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
import java.lang.ref.WeakReference

object CityListComponentHolder : ComponentHolder<CityListApi, CityListDependencies> {

    internal lateinit var componentRef: WeakReference<CityListComponent>

    override fun get(dependencyProvider: () -> CityListDependencies): CityListApi {
        var component: CityListComponent?

        synchronized(this) {
            if (componentRef.get() == null) {
                componentRef = WeakReference(CityListComponent.initAndGet(dependencyProvider()))
            }
            component = componentRef.get()
        }

        return component ?: throw IllegalStateException("Component is not initialized")
    }
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

