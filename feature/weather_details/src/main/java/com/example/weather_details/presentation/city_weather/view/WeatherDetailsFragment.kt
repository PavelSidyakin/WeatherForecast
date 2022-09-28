package com.example.weather_details.presentation.city_weather.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.weather_details.R
import com.example.weather_details.WeatherDetailsComponentHolder
import com.example.weather_details.presentation.city_weather.WeatherListItemData
import com.example.weather_details.presentation.city_weather.presenter.WeatherDetailsPresenter
import com.example.weather_details.presentation.city_weather.view.recycler_view.WeatherListAdapter
import kotlinx.android.synthetic.main.weather_fragment.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class WeatherDetailsFragment : MvpAppCompatFragment(), WeatherView {

    @InjectPresenter
    internal lateinit var presenter: WeatherDetailsPresenter

    @ProvidePresenter
    internal fun providePresenter(): WeatherDetailsPresenter =
        WeatherDetailsComponentHolder.getComponent().weatherDetailsPresenterFactory.create(arguments.getCityName())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun showCityHeader(name: String) {
        city_weather_header.text = name
    }

    override fun showCityPicture(pictureUrl: String) {
        Glide.with(city_weather_city_picture.context)
            .load(Uri.parse(pictureUrl))
            .placeholder(R.drawable.ic_city_picture_stub)
            .error(R.drawable.ic_city_picture_stub)
            .into(city_weather_city_picture)
    }

    override fun showWeatherList(cityWeather: List<WeatherListItemData>) {
        val adapter = WeatherListAdapter(cityWeather)

        city_weather_recycler_temperature.adapter = adapter

        adapter.notifyDataSetChanged()
    }

    companion object {
        const val FRAGMENT_TAG = "WeatherFragment"
        private const val KEY_CITY_NAME = "KeyCityName"

        fun newInstance(cityName: String) = WeatherDetailsFragment().apply {
            if (arguments == null) arguments = Bundle()
            arguments?.putString(KEY_CITY_NAME, cityName)
        }

        private fun Bundle?.getCityName() =
            this?.getString(KEY_CITY_NAME) ?: throw IllegalStateException("Argument $KEY_CITY_NAME is mandatory")
    }
}