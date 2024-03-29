package com.example.main_screen.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.city_list.CityListFragmentFactory
import com.example.city_list.presentation.city_list.view.CityListFragment
import com.example.main_screen.MainScreenComponentHolder
import com.example.main_screen.R
import com.example.main_screen.presentation.presenter.MainScreenPresenter
import com.example.weather_details.WeatherDetailsFragmentFactory
import com.example.weather_details.presentation.city_weather.view.WeatherDetailsFragment
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

internal class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainScreenPresenter

    @ProvidePresenter
    fun providePresenter(): MainScreenPresenter {
        return MainScreenComponentHolder.getComponent().mainScreenPresenter
    }

    @Inject
    lateinit var cityListFragmentFactory: CityListFragmentFactory

    @Inject
    lateinit var weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        MainScreenComponentHolder.getComponent().inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.main_activity_container, cityListFragmentFactory.createCityListFragment(), CITY_LIST_FRAGMENT_TAG)

            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
        }
    }

    override fun openWeatherForCity(cityName: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack(CITY_LIST_FRAGMENT_TAG)
        fragmentTransaction.replace(R.id.main_activity_container, weatherDetailsFragmentFactory.createWeatherDetailsFragment(cityName), WEATHER_DETAILS_FRAGMENT_TAG)

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

    companion object {
        private val CITY_LIST_FRAGMENT_TAG = CityListFragment::class.qualifiedName!!
        private val WEATHER_DETAILS_FRAGMENT_TAG = WeatherDetailsFragment::class.qualifiedName!!
    }
}
