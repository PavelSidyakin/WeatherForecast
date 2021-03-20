package com.example.city_list.domain

import com.example.city_list.CityListMonitor
import kotlinx.coroutines.channels.Channel

internal class CityListObserverImpl(
) : CityListMonitor, CityListUpdater {

    private val channel = Channel<String>(1)

    override fun observeCitySelection(): Channel<String> {
        return channel
    }

    override fun onCitySelected(city: String) {
        channel.offer(city)
    }
}