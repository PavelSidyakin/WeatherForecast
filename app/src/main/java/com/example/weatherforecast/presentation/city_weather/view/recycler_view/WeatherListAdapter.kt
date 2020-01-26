package com.example.weatherforecast.presentation.city_weather.view.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.presentation.city_list.view.recycler_view.CityListItemClickListener
import com.example.weatherforecast.presentation.city_list.view.recycler_view.CityListItemViewHolder
import com.example.weatherforecast.presentation.city_weather.WeatherListItemData

internal class WeatherListAdapter(private val weatherList: List<WeatherListItemData>) : RecyclerView.Adapter<WeatherListItemViewHolder>() {

    var itemClickListener: CityListItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListItemViewHolder {
        return WeatherListItemViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: WeatherListItemViewHolder, position: Int) {
        holder.bind(weatherList[position], position)
    }

}