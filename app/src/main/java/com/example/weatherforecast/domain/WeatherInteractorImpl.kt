package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.CityWeather
import com.example.weatherforecast.domain.model.UpdateWeatherResultCode
import com.example.weatherforecast.domain.model.data.WeatherOfflineSaveResultCode
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestDataItem
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestResult
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestResultCode
import java.util.Date
import javax.inject.Inject

class WeatherInteractorImpl

    @Inject
    constructor(
        private val weatherOfflineRepository: WeatherOfflineRepository,
        private val weatherOnlineRepository: WeatherOnlineRepository,
        private val fileDownloader: FileDownloader
    )
    : WeatherInteractor {

    override suspend fun requestAllCitiesInfo(): Map<String, CityInfo> {
        return weatherOfflineRepository.requestAllCitiesInfo()
    }

    override suspend fun requestCityWeather(cityName: String): Map<Date, Float> {
        return weatherOfflineRepository.requestCityWeather(cityName)
    }

    override suspend fun updateOfflineResult(): UpdateWeatherResultCode {
        val onlineResult: WeatherOnlineRequestResult = weatherOnlineRepository.requestWeather()

        if (onlineResult.resultCode == WeatherOnlineRequestResultCode.NO_NETWORK) {
            return UpdateWeatherResultCode.NO_NETWORK
        }

        if (onlineResult.resultCode != WeatherOnlineRequestResultCode.OK) {
            return UpdateWeatherResultCode.GENERAL_ERROR
        }

        val onlineData: List<WeatherOnlineRequestDataItem> = onlineResult.data
            ?: return UpdateWeatherResultCode.GENERAL_ERROR

        var saveCitiesWeatherResult = saveCitiesWeather(onlineData)
        var saveCitiesInfoResult = saveCitiesInfo(onlineData)

        if(saveCitiesWeatherResult != WeatherOfflineSaveResultCode.OK || saveCitiesInfoResult != WeatherOfflineSaveResultCode.OK) {
            // Something wrong with the local storage.
            // Try to cleanup and retry
            weatherOfflineRepository.clearAllData()
            saveCitiesWeatherResult = saveCitiesWeather(onlineData)
            saveCitiesInfoResult = saveCitiesInfo(onlineData)
        }

        return if (saveCitiesInfoResult == WeatherOfflineSaveResultCode.OK
                && saveCitiesWeatherResult == WeatherOfflineSaveResultCode.OK) {
            UpdateWeatherResultCode.OK
        } else {
            UpdateWeatherResultCode.GENERAL_ERROR
        }
    }

    private suspend fun saveCitiesWeather(onlineWeatherList: List<WeatherOnlineRequestDataItem>): WeatherOfflineSaveResultCode {
        val citiesWeather = onlineWeatherList.fold(mutableMapOf<String, CityWeather>()) { citiesWeatherAccumulator, weatherOnlineRequestDataItem ->
            val cityName = weatherOnlineRequestDataItem.city.name

            val cityWeather = citiesWeatherAccumulator[cityName]?: CityWeather(mutableMapOf()).also { citiesWeatherAccumulator[cityName] = it }

            val dateToTemperatureMap = cityWeather.dateToTemperatureMap as MutableMap
            val date = weatherOnlineRequestDataItem.date

            if (!dateToTemperatureMap.containsKey(date)) {
                dateToTemperatureMap[date] = 0f
            }

            dateToTemperatureMap[weatherOnlineRequestDataItem.date] = weatherOnlineRequestDataItem.temp
            citiesWeatherAccumulator
        }

        return weatherOfflineRepository.saveCitiesWeather(citiesWeather)
    }

    private suspend fun saveCitiesInfo(onlineWeatherList: List<WeatherOnlineRequestDataItem>): WeatherOfflineSaveResultCode {
        val citiesInfo = onlineWeatherList.fold(mutableMapOf<String, CityInfo>()) { citiesInfoAccumulator, weatherOnlineRequestDataItem ->
            val cityName = weatherOnlineRequestDataItem.city.name
            val cityPictureUrl = weatherOnlineRequestDataItem.city.picture

            if (!citiesInfoAccumulator.containsKey(cityName)) {
                val pictureBytes = fileDownloader.downloadFile(cityPictureUrl)

                if (pictureBytes != null) {
                    citiesInfoAccumulator[cityName] = CityInfo(pictureBytes)
                }
            }
            citiesInfoAccumulator
        }

        return weatherOfflineRepository.saveCitiesInfo(citiesInfo)
    }

    private companion object {
        private const val TAG = "WeatherInteractor"
    }
}