package com.example.city_list.presentation.city_list.view

import com.example.city_list.CityListFragmentFactory

internal class CityListFragmentFactoryImpl : CityListFragmentFactory {
    override fun createCityListFragment(): CityListFragment {
        return CityListFragment()
    }
}