package com.example.main_screen.di

import com.example.main_screen.MainScreenFeatureApi
import com.example.main_screen.MainScreenFeatureDependencies
import com.example.main_screen.presentation.presenter.MainScreenPresenter
import com.example.main_screen.presentation.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [MainScreenFeatureDependencies::class], modules = [MainScreenModule::class])
@Singleton
internal interface MainScreenComponent : MainScreenFeatureApi {
    fun inject(mainActivity: MainActivity)

    val mainScreenPresenter: MainScreenPresenter

    companion object {
        fun initAndGet(mainScreenDependencies: MainScreenFeatureDependencies): MainScreenComponent {
            return DaggerMainScreenComponent.builder()
                .mainScreenFeatureDependencies(mainScreenDependencies)
                .build()
        }
    }
}
