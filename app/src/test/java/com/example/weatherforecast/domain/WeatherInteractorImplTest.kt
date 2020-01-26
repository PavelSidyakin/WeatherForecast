package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.CityWeather
import com.example.weatherforecast.domain.model.UpdateOfflineResultCode
import com.example.weatherforecast.domain.model.data.WeatherOfflineSaveResultCode
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestDataItem
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestResult
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestResultCode
import com.example.weatherforecast.utils.logs.XLog
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.Date


internal class WeatherInteractorImplTest {

    @Mock
    private lateinit var weatherOfflineRepository: WeatherOfflineRepository

    @Mock
    private lateinit var weatherOnlineRepository: WeatherOnlineRepository

    @Mock
    private lateinit var timeProvider: TimeProvider

    private val generalSettingsRepository: GeneralSettingsRepository = object : GeneralSettingsRepository {
        override var lastUpdateTimeMillis: Long = 0L
    }

    private lateinit var weatherInteractor: WeatherInteractorImpl

    @BeforeEach
    fun beforeEachTest() {
        MockitoAnnotations.initMocks(this)
        XLog.enableLogging(false)
        weatherInteractor = WeatherInteractorImpl(weatherOfflineRepository, weatherOnlineRepository, generalSettingsRepository, timeProvider)
    }

    @Test
    fun `When request all cities info, should return offline data`() {
        runBlocking {
            // test data
            val name1 = "name1111"
            val url1 = "url1111"

            val name2 = "name2222"
            val url2 = "url2222"

            // when
            whenever(weatherOfflineRepository.requestAllCitiesInfo()).thenReturn(mapOf(
                name1 to CityInfo(url1),
                name2 to CityInfo(url2)
            ))

            // expected values
            val expectedCitiesInfoMap = mapOf(
                name1 to CityInfo(url1),
                name2 to CityInfo(url2)
            )

            // action
            val result = weatherInteractor.requestAllCitiesInfo()

            // verify
            assert(result == expectedCitiesInfoMap)
            verify(weatherOfflineRepository).requestAllCitiesInfo()
        }
    }

    @Test
    fun `When request city info, should return offline data`() {
        runBlocking {
            // test data
            val cityName = "nncnerjceri"

            val time1 = 111L
            val temperature1 = 111f

            val time2 = 222L
            val temperature2 = 222f

            // when
            whenever(weatherOfflineRepository.requestCityWeather(anyString())).thenReturn(mapOf(
                time1 to temperature1,
                time2 to temperature2
            ))

            // expected values
            val expectedTemperatureMap = mapOf(
                time1 to temperature1,
                time2 to temperature2
            )

            // action
            val result = weatherInteractor.requestCityWeather(cityName)

            // verify
            assert(result == expectedTemperatureMap)
            verify(weatherOfflineRepository).requestCityWeather(cityName)
        }
    }

    @Test
    fun `When request city weather, should return offline data`() {
        runBlocking {
            // test data
            val cityName = "nncnerjceri"
            val pictureUrl = "url1111"

            // when
            whenever(weatherOfflineRepository.requestCityInfo(anyString())).thenReturn(CityInfo(pictureUrl))

            // expected values
            val expectedCityInfo = CityInfo(pictureUrl)

            // action
            val result = weatherInteractor.requestCityInfo(cityName)

            // verify
            assert(result == expectedCityInfo)
            verify(weatherOfflineRepository).requestCityInfo(cityName)
        }
    }


    @Test
    fun `When getLastUpdateTime() is called, should return value from settings`() {
        val lastUpdateTimeTestVal = 2222L
        generalSettingsRepository.lastUpdateTimeMillis = lastUpdateTimeTestVal

        // action
        val lastUpdateTime = weatherInteractor.getLastUpdateTime()

        // verify
        assert(lastUpdateTime == lastUpdateTimeTestVal)
    }

    @Test
    fun `When updateAllOfflineInfo() is called, should save offline values`() {
        runBlocking {

            // Test data
            val date1 = Date(1111)
            val name1 = "1111"
            val picture1 = "url1111"
            val tempType1 = WeatherOnlineRequestDataItem.TemperatureType.FAHRENHEIT
            val temp1 = 1111f

            val date2 = Date(2222)
            val date21 = Date(22221111)
            val name2 = "2222"
            val picture2 = "url2222"
            val tempType2 = WeatherOnlineRequestDataItem.TemperatureType.KELVIN
            val temp2 = 2222f
            val tempType21 = WeatherOnlineRequestDataItem.TemperatureType.FAHRENHEIT
            val temp21 = 22221111f

            val date3 = Date(3333)
            val name3 = "3333"
            val picture3 = "url3333"
            val tempType3 = WeatherOnlineRequestDataItem.TemperatureType.CELSIUS
            val temp3 = 3333f

            val lastUpdateTime = 23131L

            // when
            whenever(weatherOnlineRepository.requestWeather()).thenReturn(WeatherOnlineRequestResult(
                WeatherOnlineRequestResultCode.OK,
                listOf(
                    WeatherOnlineRequestDataItem(date1, WeatherOnlineRequestDataItem.City(name1, picture1), tempType1, temp1),
                    WeatherOnlineRequestDataItem(date2, WeatherOnlineRequestDataItem.City(name2, picture2), tempType2, temp2),
                    WeatherOnlineRequestDataItem(date21, WeatherOnlineRequestDataItem.City(name2, picture2), tempType21, temp21),
                    WeatherOnlineRequestDataItem(date3, WeatherOnlineRequestDataItem.City(name3, picture3), tempType3, temp3)
                )
            ))
            whenever(weatherOfflineRepository.saveCitiesInfo(any())).thenReturn(WeatherOfflineSaveResultCode.OK)
            whenever(weatherOfflineRepository.saveCitiesWeather(any())).thenReturn(WeatherOfflineSaveResultCode.OK)
            whenever(timeProvider.currentTimeInMillis).thenReturn(lastUpdateTime)

            // action
            weatherInteractor.updateAllOfflineInfo()

            // expected values
            val expectedCitiesInfo: Map<String, CityInfo> = mapOf(
                name1 to CityInfo(picture1),
                name2 to CityInfo(picture2),
                name3 to CityInfo(picture3)
            )

            val expectedCitiesWeather: Map<String, CityWeather> = mapOf(
                name1 to CityWeather(mapOf(date1 to convertToCelsius(tempType1, temp1))),
                name2 to CityWeather(mapOf(date2 to convertToCelsius(tempType2, temp2), date21 to convertToCelsius(tempType21, temp21))),
                name3 to CityWeather(mapOf(date3 to convertToCelsius(tempType3, temp3)))
            )

            // verify
            verify(weatherOfflineRepository).saveCitiesInfo(expectedCitiesInfo)
            verify(weatherOfflineRepository).saveCitiesWeather(expectedCitiesWeather)
            verify(weatherOfflineRepository).clearWeatherOlderThan(anyLong())
            assert(weatherInteractor.getLastUpdateTime() == lastUpdateTime)
        }
    }

    @Nested
    inner class `When request online data failed, shouldn't try to save`{
        private val lastUpdateTime = 23131L
        private val prevLastUpdateTime = 24343L

        @BeforeEach
        fun beforeEachTest() {
            generalSettingsRepository.lastUpdateTimeMillis = prevLastUpdateTime
            // when
            runBlocking {
                whenever(weatherOfflineRepository.saveCitiesInfo(any())).thenReturn(WeatherOfflineSaveResultCode.OK)
                whenever(weatherOfflineRepository.saveCitiesWeather(any())).thenReturn(WeatherOfflineSaveResultCode.OK)
                whenever(timeProvider.currentTimeInMillis).thenReturn(lastUpdateTime)
            }
        }

        @Test
        fun `on no network error`() {
            runBlocking {
                // when
                whenever(weatherOnlineRepository.requestWeather()).thenReturn(WeatherOnlineRequestResult(
                    WeatherOnlineRequestResultCode.NO_NETWORK, null))

                // action
                val updateResult = weatherInteractor.updateAllOfflineInfo()

                // verify
                assert(updateResult == UpdateOfflineResultCode.NO_NETWORK)
            }
        }

        @Test
        fun `on general error`() {
            runBlocking {
                // when
                whenever(weatherOnlineRepository.requestWeather()).thenReturn(WeatherOnlineRequestResult(
                    WeatherOnlineRequestResultCode.GENERAL_ERROR, null))

                // action
                val updateResult = weatherInteractor.updateAllOfflineInfo()

                // verify
                assert(updateResult == UpdateOfflineResultCode.GENERAL_ERROR)
            }
        }

        @Test
        fun `on empty data`() {
            runBlocking {
                // when
                whenever(weatherOnlineRepository.requestWeather()).thenReturn(WeatherOnlineRequestResult(
                    WeatherOnlineRequestResultCode.OK, null))

                // action
                val updateResult = weatherInteractor.updateAllOfflineInfo()

                // verify
                assert(updateResult == UpdateOfflineResultCode.GENERAL_ERROR)
            }
        }

        @AfterEach
        fun afterEachTest() {
            runBlocking {
                // verify
                verify(weatherOfflineRepository, never()).saveCitiesWeather(any())
                verify(weatherOfflineRepository, never()).saveCitiesWeather(any())
                assert(weatherInteractor.getLastUpdateTime() == prevLastUpdateTime)
            }
        }
    }

    @Nested
    inner class `When data save failed, should clear data and retry` {

        @BeforeEach
        fun beforeEachTest() {
            runBlocking {
                whenever(weatherOnlineRepository.requestWeather()).thenReturn(WeatherOnlineRequestResult(
                    WeatherOnlineRequestResultCode.OK, listOf(
                        WeatherOnlineRequestDataItem(Date(), WeatherOnlineRequestDataItem.City("nn", "pp"), WeatherOnlineRequestDataItem.TemperatureType.FAHRENHEIT, 333F)
                    )
                ))
            }
        }

        @Test
        fun `on save weather OK, save info failed`() {
            // when
            runBlocking {
                whenever(weatherOfflineRepository.saveCitiesInfo(any())).thenReturn(WeatherOfflineSaveResultCode.ERROR)
                whenever(weatherOfflineRepository.saveCitiesWeather(any())).thenReturn(WeatherOfflineSaveResultCode.OK)
            }
        }

        @Test
        fun `on save weather failed, save info OK`() {
            // when
            runBlocking {
                whenever(weatherOfflineRepository.saveCitiesInfo(any())).thenReturn(WeatherOfflineSaveResultCode.OK)
                whenever(weatherOfflineRepository.saveCitiesWeather(any())).thenReturn(WeatherOfflineSaveResultCode.ERROR)
            }
        }

        @Test
        fun `on save weather failed, save info failed`() {
            // when
            runBlocking {
                whenever(weatherOfflineRepository.saveCitiesInfo(any())).thenReturn(WeatherOfflineSaveResultCode.ERROR)
                whenever(weatherOfflineRepository.saveCitiesWeather(any())).thenReturn(WeatherOfflineSaveResultCode.ERROR)
            }
        }

        @AfterEach
        fun afterEachTest() {
            runBlocking {
                // action
                weatherInteractor.updateAllOfflineInfo()

                // verify
                verify(weatherOfflineRepository).clearCitiesInfo()
                verify(weatherOfflineRepository).clearCitiesWeather()
                verify(weatherOfflineRepository, times(2)).saveCitiesWeather(any())
                verify(weatherOfflineRepository, times(2)).saveCitiesInfo(any())
            }
        }
    }

    private fun convertToCelsius(temperatureType: WeatherOnlineRequestDataItem.TemperatureType, value: Float): Float {
        return when(temperatureType) {
            WeatherOnlineRequestDataItem.TemperatureType.KELVIN -> value - 273.15f
            WeatherOnlineRequestDataItem.TemperatureType.CELSIUS -> value
            WeatherOnlineRequestDataItem.TemperatureType.FAHRENHEIT -> (value - 32) / 1.8f
        }
    }



}