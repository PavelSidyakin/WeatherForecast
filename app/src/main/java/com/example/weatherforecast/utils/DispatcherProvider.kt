package com.example.weatherforecast.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher

}