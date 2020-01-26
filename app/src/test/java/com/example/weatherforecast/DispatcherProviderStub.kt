package com.example.weatherforecast

import com.example.weatherforecast.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProviderStub : DispatcherProvider {
    override fun io(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }

    override fun main(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }
}