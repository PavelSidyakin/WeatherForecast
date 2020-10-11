package com.example.city_list

import com.example.city_list.presentation.city_list.view.CityListFragment

interface CityListFragmentFactory {
    fun createCityListFragment(): CityListFragment
}