package com.example.city_list.di

import com.example.city_list.CityListFeatureApi
import com.example.city_list.CityListFeatureDependencies
import com.example.city_list.presentation.city_list.presenter.CityListPresenter
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [CityListFeatureDependencies::class], modules = [CityListModule::class])
@Singleton
internal interface CityListComponent : CityListFeatureApi {

    val cityListPresenter: CityListPresenter

    companion object {
        fun initAndGet(cityListDependencies: CityListFeatureDependencies): CityListComponent {
            return DaggerCityListComponent.builder()
                .cityListFeatureDependencies(cityListDependencies)
                .build()
        }
    }
}
