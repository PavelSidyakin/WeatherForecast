package com.example.weatherforecast.utils.logs

import android.util.Log

object XLog {
    private var enabled = true

    fun enableLogging(enable: Boolean) {
        enabled = enable
    }

    fun v(tag: String, message: String) {
        if (enabled) Log.v(tag, message)
    }

    fun v(tag: String, s: String, th: Throwable) {
        if (enabled) Log.v(tag, s, th)
    }

    fun d(tag: String, message: String) {
        if (enabled) Log.d(tag, message)
    }

    fun d(tag: String, message: String, th: Throwable) {
        if (enabled) Log.d(tag, message, th)
    }

    fun i(tag: String, message: String) {
        if (enabled) Log.i(tag, message)
    }

    fun i(tag: String, message: String, th: Throwable) {
        if (enabled) Log.i(tag, message, th)
    }

    fun w(tag: String, message: String) {
        if (enabled) Log.w(tag, message)
    }

    fun w(tag: String, message: String, th: Throwable) {
        if (enabled) Log.w(tag, message, th)
    }

    fun e(tag: String, message: String) {
        if (enabled) Log.e(tag, message)
    }

    fun e(tag: String, message: String, th: Throwable) {
        if (enabled) Log.e(tag, message, th)
    }
}
