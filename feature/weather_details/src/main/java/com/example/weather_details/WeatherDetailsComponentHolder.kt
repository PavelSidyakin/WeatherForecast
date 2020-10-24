package com.example.weather_details

import com.example.common.coroutine_utils.DispatcherProvider
import com.example.module_injector.BaseFeatureAPI
import com.example.module_injector.BaseFeatureDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate
import com.example.weather_details.di.WeatherDetailsComponent
import com.example.weather_details.domain.WeatherDetailsRepository

object WeatherDetailsComponentHolder : ComponentHolder<WeatherDetailsFeatureApi, WeatherDetailsFeatureDependencies> {

    private val componentHolderDelegate = ComponentHolderDelegate<
            WeatherDetailsFeatureApi,
            WeatherDetailsFeatureDependencies,
            WeatherDetailsComponent> { dependencies: WeatherDetailsFeatureDependencies ->
        WeatherDetailsComponent.initAndGet(dependencies)
    }

    internal fun getComponent(): WeatherDetailsComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> WeatherDetailsFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): WeatherDetailsFeatureApi = componentHolderDelegate.get()
    
}

interface WeatherDetailsFeatureDependencies : BaseFeatureDependencies {
    val dispatcherProvider: DispatcherProvider

    val weatherDetailsRepository: WeatherDetailsRepository
}

interface WeatherDetailsFeatureApi : BaseFeatureAPI {
    val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory
}

