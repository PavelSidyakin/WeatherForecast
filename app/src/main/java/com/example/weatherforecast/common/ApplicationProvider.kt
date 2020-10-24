package com.example.weatherforecast.common

import android.content.Context
import com.example.weatherforecast.TheApplication


interface ApplicationProvider {

    /**
     * @returns application context
     */
    val applicationContext: Context
}