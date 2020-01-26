package com.example.weatherforecast.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecast.R
import com.example.weatherforecast.TheApplication
import com.example.weatherforecast.di.screen.WeatherScreenComponent
import com.example.weatherforecast.presentation.city_list.view.CityListFragment
import com.example.weatherforecast.presentation.city_weather.view.WeatherFragment

class MainActivity : AppCompatActivity() {

    val weatherComponent: WeatherScreenComponent by lazy {
        TheApplication.getAppComponent().getWeatherScreenComponent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.main_activity_container, CityListFragment(), CityListFragment.FRAGMENT_TAG)

        fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
    }

    fun openWeather() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack(CityListFragment.FRAGMENT_TAG)
        fragmentTransaction.replace(R.id.main_activity_container, WeatherFragment(), WeatherFragment.FRAGMENT_TAG)

        fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 0) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
