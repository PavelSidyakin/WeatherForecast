package com.example.city_list.presentation.city_list.presenter

import com.example.city_list.CityListComponentHolder
import com.example.city_list.domain.CityListUpdater
import com.example.city_list.domain.WeatherInteractor
import com.example.city_list.models.UpdateOfflineResultCode
import com.example.city_list.presentation.city_list.CityListItemData
import com.example.city_list.presentation.city_list.view.CityListView
import com.example.common.coroutine_utils.DispatcherProvider
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
internal class CityListPresenter @Inject constructor(
    private val weatherInteractor: WeatherInteractor,
    private val dispatcherProvider: DispatcherProvider,
    private val cityListUpdater: CityListUpdater,
) : MvpPresenter<CityListView>(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + dispatcherProvider.io()

    // Save it here to prevent garbage collection on screen rotation
    private val cityListComponent = CityListComponentHolder.getComponent()

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

            if (updateOfflineResultCode == UpdateOfflineResultCode.OK) {
                displaySavedData()
            }

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
            .map { CityListItemData(it.first, it.second.pictureUrl ?: "") }

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
        cityListUpdater.onCitySelected(name)
    }

}