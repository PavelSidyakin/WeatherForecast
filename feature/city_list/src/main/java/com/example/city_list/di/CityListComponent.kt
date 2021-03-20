package com.example.city_list.di

import com.example.city_list.CityListFeatureApi
import com.example.city_list.CityListFeatureDependencies
import com.example.city_list.presentation.city_list.presenter.CityListPresenter

internal interface CityListComponent : CityListFeatureApi {

    val cityListPresenter: CityListPresenter

    companion object {
        fun initAndGet(cityListDependencies: CityListFeatureDependencies): CityListComponent {
            return CityListComponentImpl(cityListDependencies)
        }
    }
}
