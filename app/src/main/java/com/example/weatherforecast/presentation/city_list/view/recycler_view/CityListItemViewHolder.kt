package com.example.weatherforecast.presentation.city_list.view.recycler_view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.R
import com.example.weatherforecast.presentation.city_list.CityListItemData
import kotlinx.android.synthetic.main.city_list_item.view.city_list_item_image_view_city
import kotlinx.android.synthetic.main.city_list_item.view.city_list_item_text_view_city

internal class CityListItemViewHolder

    private constructor(
        view: View,
        private val itemClickListener: CityListItemClickListener?
    ) : RecyclerView.ViewHolder(view) {

    fun bind(cityItem: CityListItemData, position: Int) {

        itemView.city_list_item_text_view_city.text = cityItem.name

        Glide.with(itemView.city_list_item_image_view_city.context)
            .load(Uri.parse(cityItem.pictureUrl))
            .placeholder(R.drawable.ic_city_picture_stub)
            .error(R.drawable.ic_city_picture_stub)
            .into(itemView.city_list_item_image_view_city)

        itemClickListener?.let {
            itemView.setOnClickListener { itemClickListener.onItemClicked(position, cityItem) }
        }

    }

    companion object {

        fun create(
            parent: ViewGroup,
            itemClickListener: CityListItemClickListener?
        ): CityListItemViewHolder {

            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.city_list_item, parent, false)

            return CityListItemViewHolder(view, itemClickListener)
        }
    }

}