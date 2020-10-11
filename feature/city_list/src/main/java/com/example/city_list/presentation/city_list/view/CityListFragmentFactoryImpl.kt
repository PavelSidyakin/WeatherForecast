package com.example.city_list.presentation.city_list.view

import androidx.fragment.app.Fragment
import com.example.city_list.CityListFragmentFactory
import javax.inject.Inject

class CityListFragmentFactoryImpl @Inject constructor() : CityListFragmentFactory {
    override fun createCityListFragment(): Fragment {
        return CityListFragment()
    }
}