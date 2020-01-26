package com.example.weatherforecast.data

import com.example.weatherforecast.domain.TimeProvider
import javax.inject.Inject

class TimeProviderImpl

    @Inject
    constructor(): TimeProvider {

    override val currentTimeInMillis: Long
        get() = System.currentTimeMillis()
}