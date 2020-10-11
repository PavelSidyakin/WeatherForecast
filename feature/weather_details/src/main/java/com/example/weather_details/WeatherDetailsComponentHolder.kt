package com.example.weather_details

import com.example.common.coroutine_utils.DispatcherProvider
import com.example.module_injector.BaseAPI
import com.example.module_injector.BaseDependencies
import com.example.module_injector.ComponentHolder
import com.example.weather_details.di.WeatherDetailsComponent
import com.example.weather_details.domain.WeatherDetailsRepository
import java.lang.ref.WeakReference

object WeatherDetailsComponentHolder : ComponentHolder<WeatherDetailsApi, WeatherDetailsDependencies> {

    internal lateinit var componentRef: WeakReference<WeatherDetailsComponent>

    override fun get(dependencyProvider: () -> WeatherDetailsDependencies): WeatherDetailsApi {
        var component: WeatherDetailsComponent?

        synchronized(this) {
            if (componentRef.get() == null) {
                componentRef = WeakReference(WeatherDetailsComponent.initAndGet(dependencyProvider()))
            }
            component = componentRef.get()
        }

        return component ?: throw IllegalStateException("Component is not initialized")
    }
}

interface WeatherDetailsDependencies : BaseDependencies {
    val dispatcherProvider: DispatcherProvider

    val weatherDetailsRepository: WeatherDetailsRepository
}

interface WeatherDetailsApi : BaseAPI {
    val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory
}

