package com.example.weatherforecast.domain

import javax.inject.Inject

class WeatherScreensSharedDataInteractorImpl

    @Inject
    constructor()

    : WeatherScreensSharedDataInteractor {

    override var currentSelectedCity: String? = null

}