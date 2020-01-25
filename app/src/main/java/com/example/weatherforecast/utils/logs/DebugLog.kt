package com.example.weatherforecast.utils.logs

import com.example.weatherforecast.BuildConfig


// Calling of a logs functions will be cut in release build (prevent creation of a log message in release build)

inline fun log(block: XLog.() -> Unit) {
    if (BuildConfig.DEBUG) XLog.block()
}
