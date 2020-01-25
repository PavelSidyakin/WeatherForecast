package com.example.weatherforecast.common

import android.content.Context
import com.example.weatherforecast.TheApplication
import javax.inject.Inject

class ApplicationProviderImpl
    @Inject
    constructor() : ApplicationProvider {

    private lateinit var theApplication: TheApplication

    override fun init(theApplication: TheApplication) {
        this.theApplication = theApplication
    }

    override val applicationContext: Context
        get() = theApplication.applicationContext


}