package com.example.city_list.di

import com.example.city_list.CityListApi
import com.example.city_list.CityListDependencies
import com.example.city_list.presentation.city_list.presenter.CityListPresenter
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [CityListDependencies::class], modules = [CityListModule::class])
@Singleton
internal interface CityListComponent : CityListApi {

    val cityListPresenter: CityListPresenter

    companion object {
        fun initAndGet(cityListDependencies: CityListDependencies): CityListComponent {
            return DaggerCityListComponent.builder()
                .cityListDependencies(cityListDependencies)
                .build()
        }
    }
}
