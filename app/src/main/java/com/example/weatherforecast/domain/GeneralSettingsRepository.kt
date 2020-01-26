package com.example.weatherforecast.domain

interface GeneralSettingsRepository {

    /**
     * Returns or sets last update in milliseconds
     */
    var lastUpdateTimeMillis: Long

}