package com.example.main_screen.di

import com.example.main_screen.MainScreenApi
import com.example.main_screen.MainScreenDependencies
import com.example.main_screen.presentation.presenter.MainScreenPresenter
import com.example.main_screen.presentation.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [MainScreenDependencies::class], modules = [MainScreenModule::class])
@Singleton
internal interface MainScreenComponent : MainScreenApi {
    fun inject(mainActivity: MainActivity)

    val mainScreenPresenter: MainScreenPresenter

    companion object {
        fun initAndGet(mainScreenDependencies: MainScreenDependencies): MainScreenComponent {
            return DaggerMainScreenComponent.builder()
                .mainScreenDependencies(mainScreenDependencies)
                .build()
        }
    }
}
