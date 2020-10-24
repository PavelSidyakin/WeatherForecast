package com.example.main_screen

import com.example.city_list.CityListFragmentFactory
import com.example.city_list.CityListMonitor
import com.example.common.coroutine_utils.DispatcherProvider
import com.example.main_screen.di.MainScreenComponent
import com.example.module_injector.BaseFeatureAPI
import com.example.module_injector.BaseFeatureDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate
import com.example.weather_details.WeatherDetailsFragmentFactory

interface MainScreenFeatureDependencies : BaseFeatureDependencies {
    val cityListFragmentFactory: CityListFragmentFactory
    val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory
    val cityListMonitor: CityListMonitor
    val dispatcherProvider: DispatcherProvider
}

interface MainScreenFeatureApi : BaseFeatureAPI

object MainScreenComponentHolder: ComponentHolder<MainScreenFeatureApi, MainScreenFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            MainScreenFeatureApi,
            MainScreenFeatureDependencies,
            MainScreenComponent> { dependencies: MainScreenFeatureDependencies ->
        MainScreenComponent.initAndGet(dependencies)
    }

    internal fun getComponent(): MainScreenComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> MainScreenFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): MainScreenFeatureApi {
        return getComponent()
    }
}

