package com.example.weather_details.presentation.city_weather.view.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_details.presentation.city_weather.WeatherListItemData

internal class WeatherListAdapter(private val weatherList: List<WeatherListItemData>) : RecyclerView.Adapter<WeatherListItemViewHolder>() {

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