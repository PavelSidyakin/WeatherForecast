package com.example.city_list.presentation.city_list.view.recycler_view

import com.example.city_list.presentation.city_list.CityListItemData


internal interface CityListItemClickListener {

    fun onItemClicked(position: Int, cityListItemData: CityListItemData)

}