package com.example.weather_details

import com.example.common.coroutine_utils.DispatcherProvider
import com.example.module_injector.BaseAPI
import com.example.module_injector.BaseDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate
import com.example.weather_details.di.WeatherDetailsComponent
import com.example.weather_details.domain.WeatherDetailsRepository

object WeatherDetailsComponentHolder : ComponentHolder<WeatherDetailsApi, WeatherDetailsDependencies> {

    private val componentHolderDelegate = ComponentHolderDelegate<
            WeatherDetailsApi,
            WeatherDetailsDependencies,
            WeatherDetailsComponent> { dependencies: WeatherDetailsDependencies ->
        WeatherDetailsComponent.initAndGet(dependencies)
    }

    internal fun getComponent(): WeatherDetailsComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> WeatherDetailsDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): WeatherDetailsApi = componentHolderDelegate.get()
    
}

interface WeatherDetailsDependencies : BaseDependencies {
    val dispatcherProvider: DispatcherProvider

    val weatherDetailsRepository: WeatherDetailsRepository
}

interface WeatherDetailsApi : BaseAPI {
    val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory
}

