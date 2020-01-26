package com.example.weatherforecast.presentation.city_list.view

import com.example.weatherforecast.presentation.city_list.CityListViewItemData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CityListView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateCityList(cityList: List<CityListViewItemData>)

    @StateStrategyType(SkipStrategy::class)
    fun clearList()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showGeneralError(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNoNetworkError(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgress(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLastUpdateTime(lastUpdateTime: Long)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openWeather()

}