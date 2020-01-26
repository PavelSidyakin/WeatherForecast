package com.example.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherforecast.domain.WeatherInteractor
import com.example.weatherforecast.domain.WeatherOfflineRepository
import com.example.weatherforecast.domain.WeatherOnlineRepository
import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.CityWeather
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestResult
import com.example.weatherforecast.utils.logs.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var weatherOnlineRepository: WeatherOnlineRepository

    @Inject
    lateinit var weatherOfflineRepository: WeatherOfflineRepository

    @Inject
    lateinit var weatherInteractor: WeatherInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        TheApplication.getAppComponent().getWeatherComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val onlineResult: WeatherOnlineRequestResult = weatherOnlineRepository.requestWeather()

//            weatherOfflineRepository.saveCitiesInfo(onlineResult.data!!.associateBy({ onlineItem ->
//                onlineItem.city.name
//            }, { onlineItem ->
//                CityInfo(ByteArray(3, { 2 }))
//            } ))
//
//            val toSave = onlineResult.data!!.fold(mutableMapOf<String, CityWeather>()) { acc, weatherOnlineRequestDataItem ->
//                val cityName = weatherOnlineRequestDataItem.city.name
//                if (!acc.containsKey(cityName)) {
//                    acc[cityName] = CityWeather(mutableMapOf())
//                }
//
//                val dateToTemperatureMap = acc[cityName]!!.dateToTemperatureMap as MutableMap
//                val date = weatherOnlineRequestDataItem.date
//
//                if (!dateToTemperatureMap.containsKey(date)) {
//                    dateToTemperatureMap[date] = 0f
//                }
//
//                dateToTemperatureMap[weatherOnlineRequestDataItem.date] = weatherOnlineRequestDataItem.temp
//                acc
//            }
//
//            weatherOfflineRepository.saveCitiesWeather(toSave)

            weatherInteractor.updateOfflineResult()

            log { i("Test", "${weatherInteractor.requestAllCitiesInfo()}") }

            log { i("Test", "${weatherInteractor.requestCityWeather("Amsterdam")}") }

        }

    }
}
