package com.example.weatherforecast.domain

interface TimeProvider {

    val currentTimeInMillis: Long

}