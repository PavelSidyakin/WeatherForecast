package com.example.common.logs

interface Logger {
    fun v(tag: String, message: String)

    fun v(tag: String, s: String, th: Throwable)

    fun d(tag: String, message: String)

    fun d(tag: String, message: String, th: Throwable)

    fun i(tag: String, message: String)

    fun i(tag: String, message: String, th: Throwable)

    fun w(tag: String, message: String)

    fun w(tag: String, message: String, th: Throwable)

    fun e(tag: String, message: String)

    fun e(tag: String, message: String, th: Throwable)
}