package com.example.main_screen.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface MainView : MvpView {
    @OneExecution
    fun openWeatherForCity(cityName: String)
}