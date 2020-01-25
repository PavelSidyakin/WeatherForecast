package com.example.weatherforecast.data

import com.example.weatherforecast.domain.WeatherOnlineRepository
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestResult
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestResultCode
import com.example.weatherforecast.domain.model.data.WeatherOnlineRequestDataItem
import com.example.weatherforecast.utils.logs.log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import javax.inject.Inject

class WeatherOnlineRepositoryImpl

    @Inject
    constructor() : WeatherOnlineRepository {

    private val retrofit: Retrofit by lazy { createRetrofit() }

    override suspend fun requestWeatherForecast(): WeatherOnlineRequestResult {
        return try {
            val weatherService = createWeatherService()

            val weatherOnlineRequestResultData = weatherService.requestWeather()

            WeatherOnlineRequestResult(WeatherOnlineRequestResultCode.OK, weatherOnlineRequestResultData)
        } catch (ioException: IOException) {
            log { w(TAG, "WeatherOnlineRepositoryImpl.requestWeatherForecast(). Failed", ioException) }
            WeatherOnlineRequestResult(WeatherOnlineRequestResultCode.NO_NETWORK, null)
        } catch (exception: Exception) {
            log { w(TAG, "WeatherOnlineRepositoryImpl.requestWeatherForecast(). Failed", exception) }
            WeatherOnlineRequestResult(WeatherOnlineRequestResultCode.GENERAL_ERROR, null)
        }.also {
            log { i(TAG, "WeatherOnlineRepositoryImpl.requestWeatherForecast(). Result = $it") }
        }
    }

    private fun createRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                log { i(TAG, message) }
            }
        })

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://us-central1-mobile-assignment-server.cloudfunctions.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createWeatherService(): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    private interface WeatherService {
        @GET("weather")
        suspend fun requestWeather(): List<WeatherOnlineRequestDataItem>
    }

    private companion object {
        private const val TAG = "WeatherOnlineRepository"
    }
}