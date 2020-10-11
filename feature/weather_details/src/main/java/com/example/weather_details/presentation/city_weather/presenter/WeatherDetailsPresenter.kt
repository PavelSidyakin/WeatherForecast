package com.example.weather_details.presentation.city_weather.presenter

import com.example.common.coroutine_utils.DispatcherProvider
import com.example.weather_details.WeatherDetailsComponentHolder
import com.example.weather_details.domain.WeatherDetailsInteractor
import com.example.weather_details.presentation.city_weather.WeatherListItemData
import com.example.weather_details.presentation.city_weather.view.WeatherView
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import kotlin.coroutines.CoroutineContext

@InjectViewState
internal class WeatherDetailsPresenter @AssistedInject constructor(
    private val weatherDetailsInteractor: WeatherDetailsInteractor,
    private val dispatcherProvider: DispatcherProvider,
    @Assisted private val cityName: String,
) : MvpPresenter<WeatherView>(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + dispatcherProvider.io()

    // Save it here to prevent garbage collection on screen rotation
    private val weatherDetailsComponent = WeatherDetailsComponentHolder.getComponent()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        launch {
            showTitle(cityName)
            showCityPicture(cityName)
            showWeatherList(cityName)
        }
    }

    private suspend fun showTitle(cityName: String) {
        withMainContext {
            viewState.showCityHeader(cityName)
        }
    }

    private suspend fun showCityPicture(cityName: String) {
        val cityPictureUrl = weatherDetailsInteractor.requestCityInfo(cityName).pictureUrl ?: return

        withMainContext {
            viewState.showCityPicture(cityPictureUrl)
        }
    }

    private suspend fun showWeatherList(cityName: String) {
        val cityWeather: Map<Long, Float> = weatherDetailsInteractor.requestCityWeather(cityName)

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

    @AssistedInject.Factory
    interface Factory {
        fun create(cityName: String): WeatherDetailsPresenter
    }
}