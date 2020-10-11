package com.example.common.time_utils

interface TimeProvider {

    /**
     * Returns current system time in milliseconds
     */
    val currentTimeInMillis: Long

}