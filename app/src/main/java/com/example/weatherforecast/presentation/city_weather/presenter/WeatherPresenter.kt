package com.example.weatherforecast.presentation.city_weather.presenter

import com.example.weatherforecast.domain.WeatherInteractor
import com.example.weatherforecast.domain.WeatherScreensSharedDataInteractor
import com.example.weatherforecast.presentation.city_weather.WeatherListItemData
import com.example.weatherforecast.presentation.city_weather.view.WeatherView
import com.example.weatherforecast.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@InjectViewState
class WeatherPresenter

    @Inject
    constructor(
        private val weatherInteractor: WeatherInteractor,
        private val sharedDataInteractor: WeatherScreensSharedDataInteractor,
        private val dispatcherProvider: DispatcherProvider
    )  :  MvpPresenter<WeatherView>(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + dispatcherProvider.io()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        launch {
            val currentSelectedCity = sharedDataInteractor.currentSelectedCity?: return@launch

            showTitle(currentSelectedCity)
            showCityPicture(currentSelectedCity)
            showWeatherList(currentSelectedCity)
        }
    }

    private suspend fun showTitle(cityName: String) {
        withMainContext {
            viewState.showCityHeader(cityName)
        }
    }

    private suspend fun showCityPicture(cityName: String) {
        val cityPictureUrl = weatherInteractor.requestCityInfo(cityName).pictureUrl?:return

        withMainContext {
            viewState.showCityPicture(cityPictureUrl)
        }
    }

    private suspend fun showWeatherList(cityName: String) {
        val cityWeather: Map<Long, Float> = weatherInteractor.requestCityWeather(cityName)

        if (cityWeather.isEmpty()) {
            return
        }

        val cityWeatherListData: List<WeatherListItemData> = cityWeather.toList()
            .map { WeatherListItemData(it.first, it.second) }

        withMainContext {
            viewState.showWeatherList(cityWeatherListData)
        }
    }

    private suspend fun <T> withMainContext(block: suspend CoroutineScope.() -> T): T {
        return withContext(dispatcherProvider.main()) {
            block()
        }
    }

}