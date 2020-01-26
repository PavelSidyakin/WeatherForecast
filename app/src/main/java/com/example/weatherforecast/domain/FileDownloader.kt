package com.example.weatherforecast.domain

interface FileDownloader {

    suspend fun downloadFile(url: String): ByteArray?

}