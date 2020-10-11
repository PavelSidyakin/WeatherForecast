package com.example.main_screen

import com.example.city_list.CityListFragmentFactory
import com.example.city_list.CityListMonitor
import com.example.common.coroutine_utils.DispatcherProvider
import com.example.main_screen.di.MainScreenComponent
import com.example.module_injector.BaseAPI
import com.example.module_injector.BaseDependencies
import com.example.module_injector.ComponentHolder
import com.example.weather_details.WeatherDetailsFragmentFactory
import java.lang.ref.WeakReference

object MainScreenComponentHolder : ComponentHolder<MainScreenApi, MainScreenDependencies> {

    internal var componentRef: WeakReference<MainScreenComponent>? = null

    override var dependencyProvider: (() -> MainScreenDependencies)? = null

    internal fun getComponent(): MainScreenComponent {
        var component: MainScreenComponent? = null

        synchronized(this) {
            dependencyProvider?.let { dependencyProvider ->
                if (componentRef?.get() == null) {
                    componentRef = WeakReference(MainScreenComponent.initAndGet(dependencyProvider()))
                }
                component = componentRef?.get()
            } ?: throw IllegalStateException("dependencyProvider is not initialized")
        }

        return component ?: throw IllegalStateException("Component is not initialized")
    }

    override fun get(): MainScreenApi {
        return getComponent()
    }
}

interface MainScreenDependencies : BaseDependencies {
    val cityListFragmentFactory: CityListFragmentFactory
    val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory
    val cityListMonitor: CityListMonitor
    val dispatcherProvider: DispatcherProvider
}

interface MainScreenApi : BaseAPI {
}

