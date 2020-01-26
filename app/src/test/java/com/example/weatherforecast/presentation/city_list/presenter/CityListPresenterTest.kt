package com.example.weatherforecast.presentation.city_list.presenter

import com.example.weatherforecast.DispatcherProviderStub
import com.example.weatherforecast.domain.WeatherInteractor
import com.example.weatherforecast.domain.WeatherScreensSharedDataInteractor
import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.UpdateOfflineResultCode
import com.example.weatherforecast.presentation.city_list.CityListItemData
import com.example.weatherforecast.presentation.city_list.view.CityListView
import com.example.weatherforecast.utils.DispatcherProvider
import com.example.weatherforecast.utils.logs.XLog
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


internal class CityListPresenterTest {

    @Mock
    private lateinit var weatherInteractor: WeatherInteractor

    @Mock
    private lateinit var sharedDataInteractor: WeatherScreensSharedDataInteractor

    @Mock
    private lateinit var cityListView: CityListView

    private val dispatcherProvider: DispatcherProvider = DispatcherProviderStub()

    private lateinit var cityListPresenter: CityListPresenter

    // test data
    private val cityName1 = "nnnn1111"
    private val cityPictureUrl1 = "uuuu1111"

    private val cityName2 = "nnnn2222"
    private val cityPictureUrl2 = "uuuu2222"

    private val citiesInfo = mapOf(
        cityName1 to CityInfo(cityPictureUrl1),
        cityName2 to CityInfo(cityPictureUrl2)
    )

    private val expectedCityListItemsData = listOf(
        CityListItemData(cityName1, cityPictureUrl1),
        CityListItemData(cityName2, cityPictureUrl2)
    )

    @BeforeEach
    fun beforeEachTest() {
        XLog.enableLogging(false)
        MockitoAnnotations.initMocks(this)

        cityListPresenter = CityListPresenter(weatherInteractor, dispatcherProvider, sharedDataInteractor)
    }

    @Test
    fun `When view is attached and data has been updated, should display saved data`() {
        runBlocking {
            // when
            whenever(weatherInteractor.requestAllCitiesInfo()).thenReturn(citiesInfo)
            whenever(weatherInteractor.getLastUpdateTime()).thenReturn(1) // To prevent auto update in onFirstViewAttach

            // action
            cityListPresenter.attachView(cityListView)

            // verify
            verify(cityListView).updateCityList(expectedCityListItemsData)
        }
    }

    @Test
    fun `When view is attached and data has NOT been updated, should try to update and display saved data`() {
        runBlocking {
            // when
            whenever(weatherInteractor.updateAllOfflineInfo()).thenReturn(UpdateOfflineResultCode.OK)
            whenever(weatherInteractor.requestAllCitiesInfo()).thenReturn(citiesInfo)
            whenever(weatherInteractor.getLastUpdateTime()).thenReturn(0)

            // action
            cityListPresenter.attachView(cityListView)

            // verify
            verify(cityListView).updateCityList(expectedCityListItemsData)
            verify(weatherInteractor).requestAllCitiesInfo()
            val inOrder = inOrder(cityListView)
            inOrder.verify(cityListView).showProgress(true)
            inOrder.verify(cityListView).showProgress(false)
        }
    }

    @Nested
    inner class `When update offline info is clicked, should show progress` {
        @BeforeEach
        fun beforeEachTest() {
            runBlocking {
                // when
                whenever(weatherInteractor.requestAllCitiesInfo()).thenReturn(mapOf())
                whenever(weatherInteractor.getLastUpdateTime()).thenReturn(1) // To prevent auto update in onFirstViewAttach
            }

            cityListPresenter.attachView(cityListView)
        }

        @Test
        fun `When interactor returns no network error, should display no network message`() {
            runBlocking {
                // when
                whenever(weatherInteractor.updateAllOfflineInfo()).thenReturn(UpdateOfflineResultCode.NO_NETWORK)

                // action
                cityListPresenter.onUpdateClicked()

                // verify
                verify(cityListView).showNoNetworkError(true)
                verify(cityListView).showGeneralError(false)
            }
        }

        @Test
        fun `When interactor returns general error, should display general error message`() {
            runBlocking {
                // when
                whenever(weatherInteractor.updateAllOfflineInfo()).thenReturn(UpdateOfflineResultCode.GENERAL_ERROR)

                // action
                cityListPresenter.onUpdateClicked()

                // verify
                verify(cityListView).showNoNetworkError(false)
                verify(cityListView).showGeneralError(true)
            }
        }

        @Test
        fun `When interactor returns OK result, should display result in list`() {
            runBlocking {
                // when
                whenever(weatherInteractor.updateAllOfflineInfo()).thenReturn(UpdateOfflineResultCode.OK)
                whenever(weatherInteractor.requestAllCitiesInfo()).thenReturn(citiesInfo)

                // action
                cityListPresenter.onUpdateClicked()

                // verify
                verify(cityListView).updateCityList(expectedCityListItemsData)
            }
        }

        @AfterEach
        fun afterEachTest() {
            // verify
            val inOrder = inOrder(cityListView)
            inOrder.verify(cityListView).showProgress(true)
            inOrder.verify(cityListView).showProgress(false)
        }

    }
}