package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.CityWeather
import com.example.weatherforecast.domain.model.UpdateOfflineResultCode
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
        private val weatherOnlineRepository: WeatherOnlineRepository
    )
    : WeatherInteractor {

    override suspend fun requestAllCitiesInfo(): Map<String, CityInfo> {
        return weatherOfflineRepository.requestAllCitiesInfo()
    }

    override suspend fun requestCityWeather(cityName: String): Map<Date, Float> {
        return weatherOfflineRepository.requestCityWeather(cityName)
    }

    override suspend fun updateOfflineWeather(): UpdateOfflineResultCode {
        return saveOffline( { saveCitiesWeather(it) }, { weatherOfflineRepository.clearCitiesWeather() } )
    }

    override suspend fun updateOfflineCityInfo(): UpdateOfflineResultCode {
        return saveOffline( { saveCitiesInfo(it) }, { weatherOfflineRepository.clearCitiesInfo() } )
    }

    override suspend fun updateAllOfflineInfo(): UpdateOfflineResultCode {
        return saveOffline( {
            val saveOfflineWeatherResult = saveCitiesWeather(it)
            val saveOfflineCityInfoResult = saveCitiesInfo(it)

            return@saveOffline if (saveOfflineCityInfoResult == WeatherOfflineSaveResultCode.OK
                && saveOfflineWeatherResult == WeatherOfflineSaveResultCode.OK) {
                WeatherOfflineSaveResultCode.OK
            } else {
                WeatherOfflineSaveResultCode.ERROR
            }
        }, {
            weatherOfflineRepository.clearCitiesWeather()
            weatherOfflineRepository.clearCitiesInfo()
        } )
    }

    private suspend fun saveOffline(
        saveBlock: suspend (onlineWeatherList: List<WeatherOnlineRequestDataItem>) -> WeatherOfflineSaveResultCode,
        clearDataBlock: suspend () -> Unit
    ): UpdateOfflineResultCode {
        val onlineResult: WeatherOnlineRequestResult = weatherOnlineRepository.requestWeather()

        if (onlineResult.resultCode == WeatherOnlineRequestResultCode.NO_NETWORK) {
            return UpdateOfflineResultCode.NO_NETWORK
        }

        if (onlineResult.resultCode != WeatherOnlineRequestResultCode.OK) {
            return UpdateOfflineResultCode.GENERAL_ERROR
        }

        val onlineData: List<WeatherOnlineRequestDataItem> = onlineResult.data
            ?: return UpdateOfflineResultCode.GENERAL_ERROR

        var saveOfflineResult = saveBlock(onlineData)

        if(saveOfflineResult != WeatherOfflineSaveResultCode.OK) {
            // Something wrong with the local storage.
            // Try to cleanup and retry
            clearDataBlock()
            saveOfflineResult = saveBlock(onlineData)
        }

        return if (saveOfflineResult == WeatherOfflineSaveResultCode.OK) {
            UpdateOfflineResultCode.OK
        } else {
            UpdateOfflineResultCode.GENERAL_ERROR
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
                citiesInfoAccumulator[cityName] = CityInfo(cityPictureUrl)
            }
            citiesInfoAccumulator
        }

        return weatherOfflineRepository.saveCitiesInfo(citiesInfo)
    }

    private companion object {
        private const val TAG = "WeatherInteractor"
    }
}