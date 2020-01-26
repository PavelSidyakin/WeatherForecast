package com.example.weatherforecast.presentation.city_list.presenter

import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.domain.WeatherInteractor
import com.example.weatherforecast.domain.WeatherScreensSharedDataInteractor
import com.example.weatherforecast.domain.model.UpdateOfflineResultCode
import com.example.weatherforecast.presentation.city_list.CityListViewItemData
import com.example.weatherforecast.presentation.city_list.view.CityListView
import com.example.weatherforecast.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@InjectViewState
class CityListPresenter

    @Inject
    constructor(
        private val weatherInteractor: WeatherInteractor,
        private val dispatcherProvider: DispatcherProvider,
        private val sharedDataInteractor: WeatherScreensSharedDataInteractor
    ) :  MvpPresenter<CityListView>(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + dispatcherProvider.io()

    private var updateJob: Job? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        if (weatherInteractor.getLastUpdateTime() == 0L) {
            updateListAsync()
        } else {
            displaySavedDataAsync()
        }
    }

    private fun updateListAsync() {
        updateJob?.cancel()

        updateJob = launch {

            hideAllErrors()

            withMainContext { viewState.showProgress(true) }

            val updateOfflineResultCode = weatherInteractor.updateAllOfflineInfo()

            if (updateOfflineResultCode == UpdateOfflineResultCode.NO_NETWORK) {
                withMainContext { viewState.showNoNetworkError(true) }
            } else if (updateOfflineResultCode == UpdateOfflineResultCode.GENERAL_ERROR) {
                withMainContext { viewState.showGeneralError(true) }
            }

            displaySavedData()

            withMainContext { viewState.showProgress(false) }
        }
    }

    private fun displaySavedDataAsync() {
        launch {
            displaySavedData()
        }
    }

    private suspend fun displaySavedData() {
        val cityList = weatherInteractor.requestAllCitiesInfo()
            .toList()
            .map { CityListViewItemData(it.first, it.second.pictureUrl?:"") }

        withMainContext { viewState.updateCityList(cityList) }

        if (weatherInteractor.getLastUpdateTime() != 0L) {
            withMainContext { viewState.showLastUpdateTime(weatherInteractor.getLastUpdateTime()) }
        }

    }

    private suspend fun hideAllErrors() {
        withMainContext {
            viewState.showNoNetworkError(false)
            viewState.showGeneralError(false)
        }
    }

    private suspend fun <T> withMainContext(block: suspend CoroutineScope.() -> T): T {
        return withContext(dispatcherProvider.main()) {
            block()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    fun onUpdateClicked() {
        updateListAsync()
    }

    fun onCityClicked(name: String) {
        sharedDataInteractor.currentSelectedCity = name
        viewState.openWeather()
    }

}