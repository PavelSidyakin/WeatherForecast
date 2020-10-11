package com.example.weatherforecast.data

import android.content.Context
import com.example.city_list.domain.GeneralSettingsRepository
import com.example.weatherforecast.common.ApplicationProvider
import javax.inject.Inject

class GeneralSettingsRepositoryImpl
    @Inject
    constructor(private val applicationProvider: ApplicationProvider)
    : GeneralSettingsRepository {

    private val generalSharedPreferences by lazy {
        applicationProvider.applicationContext.getSharedPreferences("general", Context.MODE_PRIVATE)
    }

    override var lastUpdateTimeMillis: Long
        get() = generalSharedPreferences.getLong(LAST_UPDATE_TIME_SETTING, 0)
        set(value) { generalSharedPreferences.edit().putLong(LAST_UPDATE_TIME_SETTING, value).apply() }

    private companion object {
        const val LAST_UPDATE_TIME_SETTING = "lastUpdateTime"
    }
}