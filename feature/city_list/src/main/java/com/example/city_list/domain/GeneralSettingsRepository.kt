package com.example.city_list.domain

interface GeneralSettingsRepository {

    /**
     * Returns or sets last update in milliseconds
     */
    var lastUpdateTimeMillis: Long

}