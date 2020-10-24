package com.example.weatherforecast.common

import android.content.Context
import com.example.weatherforecast.TheApplication

class ApplicationProviderImpl(private val theApplication: TheApplication) : ApplicationProvider {

    override val applicationContext: Context
        get() = theApplication.applicationContext


}