package com.example.common.logs

import com.example.common.BuildConfig


// Calling of a logs functions will be cut in release build (prevent creation of a log message in release build)

inline fun log(block: XLog.() -> Unit) {
    if (BuildConfig.DEBUG) XLog.block()
}
