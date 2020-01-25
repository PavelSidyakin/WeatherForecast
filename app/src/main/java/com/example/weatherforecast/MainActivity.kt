package com.example.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherforecast.domain.WeatherOnlineRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var weatherOnlineRepository: WeatherOnlineRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        TheApplication.getAppComponent().getWeatherComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            weatherOnlineRepository.requestWeatherForecast()
        }

    }
}
