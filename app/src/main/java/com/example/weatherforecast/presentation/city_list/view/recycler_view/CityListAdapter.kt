package com.example.weatherforecast.presentation.city_list.view.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.presentation.city_list.CityListItemData

internal class CityListAdapter(private val cities: List<CityListItemData>) : RecyclerView.Adapter<CityListItemViewHolder>() {

    var itemClickListener: CityListItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListItemViewHolder {
        return CityListItemViewHolder.create(parent, itemClickListener)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CityListItemViewHolder, position: Int) {
        holder.bind(cities[position], position)
    }

}