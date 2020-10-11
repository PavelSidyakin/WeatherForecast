package com.example.main_screen

import com.example.city_list.CityListFragmentFactory
import com.example.city_list.CityListMonitor
import com.example.common.coroutine_utils.DispatcherProvider
import com.example.main_screen.di.MainScreenComponent
import com.example.module_injector.BaseAPI
import com.example.module_injector.BaseDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate
import com.example.weather_details.WeatherDetailsFragmentFactory

object MainScreenComponentHolder : ComponentHolder<MainScreenApi, MainScreenDependencies> {

    private val componentHolderDelegate = ComponentHolderDelegate<
            MainScreenApi,
            MainScreenDependencies,
            MainScreenComponent> { dependencies: MainScreenDependencies ->
        MainScreenComponent.initAndGet(dependencies)
    }

    internal fun getComponent(): MainScreenComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> MainScreenDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): MainScreenApi = componentHolderDelegate.get()
}

interface MainScreenDependencies : BaseDependencies {
    val cityListFragmentFactory: CityListFragmentFactory
    val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory
    val cityListMonitor: CityListMonitor
    val dispatcherProvider: DispatcherProvider
}

interface MainScreenApi : BaseAPI {
}

