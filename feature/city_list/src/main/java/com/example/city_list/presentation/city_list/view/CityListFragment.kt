package com.example.city_list.presentation.city_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.city_list.CityListComponentHolder
import com.example.city_list.R
import com.example.city_list.presentation.city_list.CityListItemData
import com.example.city_list.presentation.city_list.presenter.CityListPresenter
import com.example.city_list.presentation.city_list.view.recycler_view.CityListAdapter
import com.example.city_list.presentation.city_list.view.recycler_view.CityListItemClickListener
import kotlinx.android.synthetic.main.city_list_fragment.city_list_button_update
import kotlinx.android.synthetic.main.city_list_fragment.city_list_progress
import kotlinx.android.synthetic.main.city_list_fragment.city_list_recycler_list
import kotlinx.android.synthetic.main.city_list_fragment.city_list_text_view_error
import kotlinx.android.synthetic.main.city_list_fragment.city_list_text_view_last_update
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.text.SimpleDateFormat
import java.util.Date

class CityListFragment : MvpAppCompatFragment(), CityListView, CityListItemClickListener {

    @InjectPresenter
    lateinit var presenter: CityListPresenter

    @ProvidePresenter
    fun providePresenter(): CityListPresenter {
        return CityListComponentHolder.componentRef.get()!!.cityListPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.city_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        city_list_button_update.setOnClickListener { presenter.onUpdateClicked() }
    }

    override fun showLastUpdateTime(lastUpdateTime: Long) {
        city_list_text_view_last_update.text = getString(R.string.city_list_last_update_time_format,
            SimpleDateFormat.getInstance().format(Date(lastUpdateTime)))
    }

    override fun updateCityList(cityList: List<CityListItemData>) {
        val adapter = CityListAdapter(cityList)
        adapter.itemClickListener = this

        city_list_recycler_list.adapter = adapter

        adapter.notifyDataSetChanged()
    }

    override fun clearList() {
        city_list_recycler_list.adapter = CityListAdapter(listOf())
            .apply { notifyDataSetChanged() }
    }

    override fun showGeneralError(show: Boolean) {
        if (show) {
            city_list_text_view_error.visibility = View.VISIBLE
            city_list_text_view_error.text = getString(R.string.city_list_error_general)
        } else {
            city_list_text_view_error.visibility = View.GONE
        }
    }

    override fun showNoNetworkError(show: Boolean) {
        if (show) {
            city_list_text_view_error.visibility = View.VISIBLE
            city_list_text_view_error.text = getString(R.string.city_list_error_no_network)
        } else {
            city_list_text_view_error.visibility = View.GONE
        }
    }

    override fun showProgress(show: Boolean) {
        city_list_progress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun onItemClicked(position: Int, cityListItemData: CityListItemData) {
        presenter.onCityClicked(cityListItemData.name)
    }
}