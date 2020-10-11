package com.example.weather_details

import com.example.common.coroutine_utils.DispatcherProvider
import com.example.module_injector.BaseAPI
import com.example.module_injector.BaseDependencies
import com.example.module_injector.ComponentHolder
import com.example.weather_details.di.WeatherDetailsComponent
import com.example.weather_details.domain.WeatherDetailsRepository
import java.lang.ref.WeakReference

object WeatherDetailsComponentHolder : ComponentHolder<WeatherDetailsApi, WeatherDetailsDependencies> {

    private var componentRef: WeakReference<WeatherDetailsComponent>? = null

    override var dependencyProvider: (() -> WeatherDetailsDependencies)? = null

    internal fun getComponent(): WeatherDetailsComponent {
        var component: WeatherDetailsComponent? = null

        synchronized(this) {
            dependencyProvider?.let { dependencyProvider ->
                if (componentRef?.get() == null) {
                    componentRef =
                        WeakReference(WeatherDetailsComponent.initAndGet(dependencyProvider()))
                }
                component = componentRef?.get()
            } ?: throw IllegalStateException("dependencyProvider is not initialized")
        }

        return component ?: throw IllegalStateException("Component is not initialized")
    }

    override fun get(): WeatherDetailsApi {
        return getComponent()
    }
}

interface WeatherDetailsDependencies : BaseDependencies {
    val dispatcherProvider: DispatcherProvider

    val weatherDetailsRepository: WeatherDetailsRepository
}

interface WeatherDetailsApi : BaseAPI {
    val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory
}

