package com.example.weatherforecast.domain

interface WeatherScreensSharedDataInteractor {

    /**
     * Returns or sets current selected city on the screen.
     */
    var currentSelectedCity: String?

}