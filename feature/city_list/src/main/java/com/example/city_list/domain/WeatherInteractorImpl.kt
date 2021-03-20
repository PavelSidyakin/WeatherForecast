package com.example.city_list.domain

import com.example.city_list.models.UpdateOfflineResultCode
import com.example.city_list.models.data.WeatherOfflineSaveResultCode
import com.example.city_list.models.data.WeatherOnlineRequestDataItem
import com.example.city_list.models.data.WeatherOnlineRequestResult
import com.example.city_list.models.data.WeatherOnlineRequestResultCode
import com.example.common.logs.log
import com.example.common.models.CityInfo
import com.example.common.models.CityWeather
import com.example.common.time_utils.TimeProvider

internal class WeatherInteractorImpl(
    private val weatherOfflineRepository: WeatherOfflineRepository,
    private val weatherOnlineRepository: WeatherOnlineRepository,
    private val generalSettingsRepository: GeneralSettingsRepository,
    private val timeProvider: TimeProvider
) : WeatherInteractor {
    override suspend fun requestAllCitiesInfo(): Map<String, CityInfo> {
        return weatherOfflineRepository.requestAllCitiesInfo()
    }

    override suspend fun requestCityWeather(cityName: String): Map<Long, Float> {
        return weatherOfflineRepository.requestCityWeather(cityName)
    }

    override suspend fun requestCityInfo(cityName: String): CityInfo {
        return weatherOfflineRepository.requestCityInfo(cityName)
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
        } , {
            weatherOfflineRepository.clearCitiesWeather()
            weatherOfflineRepository.clearCitiesInfo()
        }
        ).also { resultCode ->
            if (resultCode == UpdateOfflineResultCode.OK) {
                generalSettingsRepository.lastUpdateTimeMillis = timeProvider.currentTimeInMillis
                weatherOfflineRepository.clearWeatherOlderThan(generalSettingsRepository.lastUpdateTimeMillis)
            }
        }
    }

    override fun getLastUpdateTime(): Long {
        return generalSettingsRepository.lastUpdateTimeMillis
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

        log { i(TAG, "WeatherInteractorImpl.saveOffline(). saveOfflineResult=$saveOfflineResult") }

        if(saveOfflineResult != WeatherOfflineSaveResultCode.OK) {
            log { i(TAG, "WeatherInteractorImpl.saveOffline(). Something wrong with the local storage. Trying to cleanup and retry...") }

            clearDataBlock()
            saveOfflineResult = saveBlock(onlineData)
            log { i(TAG, "WeatherInteractorImpl.saveOffline(). saveOfflineResult=$saveOfflineResult") }
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

            dateToTemperatureMap[weatherOnlineRequestDataItem.date] = convertTemperatureToCelsius(
                weatherOnlineRequestDataItem.tempType,
                weatherOnlineRequestDataItem.temp)

            citiesWeatherAccumulator
        }

        return weatherOfflineRepository.saveCitiesWeather(citiesWeather).also {
            log { i(TAG, "WeatherInteractorImpl.saveCitiesWeather(). result=$it") }
        }
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

        return weatherOfflineRepository.saveCitiesInfo(citiesInfo).also {
            log { i(TAG, "WeatherInteractorImpl.saveCitiesInfo(). result=$it") }
        }
    }

    private fun convertTemperatureToCelsius(temperatureType: WeatherOnlineRequestDataItem.TemperatureType, value: Float): Float {
        return when(temperatureType) {
            WeatherOnlineRequestDataItem.TemperatureType.KELVIN -> value - 273.15f
            WeatherOnlineRequestDataItem.TemperatureType.CELSIUS -> value
            WeatherOnlineRequestDataItem.TemperatureType.FAHRENHEIT -> (value - 32) / 1.8f
        }
    }

    private companion object {
        private const val TAG = "WeatherInteractor"
    }

}