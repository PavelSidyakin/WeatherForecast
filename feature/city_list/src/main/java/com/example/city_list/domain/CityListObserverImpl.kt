package com.example.city_list.domain

import com.example.city_list.CityListMonitor
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityListObserverImpl @Inject constructor(
) : CityListMonitor, CityListUpdater {

    private val channel = Channel<String>(1)

    override fun observeCitySelection(): Channel<String> {
        return channel
    }

    override fun onCitySelected(city: String) {
        channel.offer(city)
    }
}