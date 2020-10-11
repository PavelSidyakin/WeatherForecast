package com.example.city_list

import kotlinx.coroutines.channels.Channel

interface CityListMonitor {
    /**
     * Observes city selection in the city list.
     *
     * @return [Channel] with the city name
     */
    fun observeCitySelection(): Channel<String>
}