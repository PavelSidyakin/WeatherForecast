package com.example.city_list.presentation.city_list.view

import com.example.city_list.CityListFragmentFactory
import javax.inject.Inject

internal class CityListFragmentFactoryImpl @Inject constructor() : CityListFragmentFactory {
    override fun createCityListFragment(): CityListFragment {
        return CityListFragment()
    }
}