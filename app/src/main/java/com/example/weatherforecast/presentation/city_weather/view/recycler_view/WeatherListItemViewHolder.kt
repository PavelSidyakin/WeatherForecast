package com.example.weatherforecast.presentation.city_weather.view.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.presentation.city_weather.WeatherListItemData
import kotlinx.android.synthetic.main.weather_list_item.view.weather_list_item_date
import kotlinx.android.synthetic.main.weather_list_item.view.weather_list_item_temperature
import java.text.SimpleDateFormat
import java.util.Date

internal class WeatherListItemViewHolder

    private constructor(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(weather: WeatherListItemData, position: Int) {

        itemView.weather_list_item_date.text = SimpleDateFormat.getInstance().format(Date(weather.time))
        itemView.weather_list_item_temperature.text = itemView.context.getString(R.string.weather_list_item_temperature_format, weather.temperature)

        // Paint items in different colors
        if (position % 2 != 0) {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.cities_search_recycler_item_odd));
        } else {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.cities_search_recycler_item_even));
        }
    }

    companion object {

        fun create(parent: ViewGroup): WeatherListItemViewHolder {

            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_list_item, parent, false)

            return WeatherListItemViewHolder(view)
        }
    }

}