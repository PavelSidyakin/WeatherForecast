package com.example.common.time_utils

import javax.inject.Inject

internal class TimeProviderImpl @Inject constructor(
): TimeProvider {

    override val currentTimeInMillis: Long
        get() = System.currentTimeMillis()
}