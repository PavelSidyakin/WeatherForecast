package com.example.main_screen.presentation.presenter

import com.example.city_list.CityListMonitor
import com.example.common.coroutine_utils.DispatcherProvider
import com.example.main_screen.MainScreenComponentHolder
import com.example.main_screen.presentation.view.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@InjectViewState
class MainScreenPresenter @Inject constructor(
    private val cityListMonitor: CityListMonitor,
    private val dispatcherProvider: DispatcherProvider,
) : MvpPresenter<MainView>(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + dispatcherProvider.io()

    // Save it here to prevent garbage collection on screen rotation
    private val mainScreenComponent = MainScreenComponentHolder.getComponent()

    override fun onFirstViewAttach() {
        launch {
            cityListMonitor.observeCitySelection()
                .consumeAsFlow()
                .collectLatest { cityName ->
                    withContext(dispatcherProvider.main()) {
                        viewState.openWeatherForCity(cityName)
                    }
                }
        }
    }

}