package com.example.weatherforecast.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecast.R
import com.example.weatherforecast.TheApplication
import com.example.weatherforecast.di.screen.WeatherScreenComponent
import com.example.weatherforecast.presentation.city_list.view.CityListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.main_activity_container, CityListFragment())

        fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
    }

    fun openWeather() {

    }

    companion object {
        val weatherComponent: WeatherScreenComponent by lazy {
            TheApplication.getAppComponent().getWeatherScreenComponent()
        }
    }
}
