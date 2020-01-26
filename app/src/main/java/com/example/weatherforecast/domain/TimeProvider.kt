package com.example.weatherforecast.domain

interface TimeProvider {

    /**
     * Returns current system time in milliseconds
     */
    val currentTimeInMillis: Long

}