package com.example.city_list.presentation.city_list.view

import com.example.city_list.presentation.city_list.CityListItemData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

internal interface CityListView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateCityList(cityList: List<CityListItemData>)

    @StateStrategyType(SkipStrategy::class)
    fun clearList()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showGeneralError(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNoNetworkError(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgress(show: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLastUpdateTime(lastUpdateTime: Long)

}