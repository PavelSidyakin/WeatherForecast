package com.example.common.logs

import com.example.common.CommonComponentHolder

object XLog {
    private var enabled = true

    private val logger: Logger? by lazy {
        CommonComponentHolder.getComponent().logger
    }

    fun enableLogging(enable: Boolean) {
        enabled = enable
    }

    fun v(tag: String, message: String) {
        if (enabled) logger?.v(tag, message)
    }

    fun v(tag: String, s: String, th: Throwable) {
        if (enabled) logger?.v(tag, s, th)
    }

    fun d(tag: String, message: String) {
        if (enabled) logger?.d(tag, message)
    }

    fun d(tag: String, message: String, th: Throwable) {
        if (enabled) logger?.d(tag, message, th)
    }

    fun i(tag: String, message: String) {
        if (enabled) logger?.i(tag, message)
    }

    fun i(tag: String, message: String, th: Throwable) {
        if (enabled) logger?.i(tag, message, th)
    }

    fun w(tag: String, message: String) {
        if (enabled) logger?.w(tag, message)
    }

    fun w(tag: String, message: String, th: Throwable) {
        if (enabled) logger?.w(tag, message, th)
    }

    fun e(tag: String, message: String) {
        if (enabled) logger?.e(tag, message)
    }

    fun e(tag: String, message: String, th: Throwable) {
        if (enabled) logger?.e(tag, message, th)
    }
}
