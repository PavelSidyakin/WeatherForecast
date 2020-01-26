package com.example.weatherforecast.data

import com.example.weatherforecast.domain.FileDownloader
import com.example.weatherforecast.utils.DispatcherProvider
import com.example.weatherforecast.utils.logs.log
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FileDownloaderImpl
    @Inject
    constructor(private val dispatcherProvider: DispatcherProvider)
    : FileDownloader {

    private val client: OkHttpClient by lazy { createOkHttpClient() }

    override suspend fun downloadFile(url: String): ByteArray? {
        return withContext(dispatcherProvider.io()) {

            log { i(TAG, "FileDownloaderImpl.downloadFile(). url = [${url}]") }

            val request = Request.Builder()
                .url(url)
                .build()

            try {
                val response: Response = client.newCall(request).await()

                if (!response.isSuccessful) {
                    return@withContext null
                }

                return@withContext response.body?.bytes()
            } catch (exception: Exception) {
                return@withContext null
            }
        }

    }

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                log { i(TAG, message) }
            }
        })

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
    private suspend fun Call.await(): Response = suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                continuation.resume(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                // Don't bother with resuming the continuation if it is already cancelled.
                if (continuation.isCancelled) return
                continuation.resumeWithException(e)
            }
        })

        continuation.invokeOnCancellation {
            try {
                cancel()
            } catch (ex: Throwable) {
                //Ignore cancel exception
            }
        }
    }

    private companion object {
        const val TAG = "FileDownloader"
    }
}