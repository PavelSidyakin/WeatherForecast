package com.example.weatherforecast.data

import android.content.Context
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.domain.GeneralSettingsRepository
import javax.inject.Inject

class GeneralSettingsRepositoryImpl
    @Inject
    constructor(private val applicationProvider: ApplicationProvider)
    : GeneralSettingsRepository {

    private val generalSharedPreferences by lazy {
        applicationProvider.applicationContext.getSharedPreferences("general", Context.MODE_PRIVATE)
    }

    override var lastUpdateTime: Long
        get() = generalSharedPreferences.getLong(LAST_UPDATE_TIME_SETTING, 0)
        set(value) { generalSharedPreferences.edit().putLong(LAST_UPDATE_TIME_SETTING, value).apply() }

    private companion object {
        const val LAST_UPDATE_TIME_SETTING = "lastUpdateTime"
    }

}