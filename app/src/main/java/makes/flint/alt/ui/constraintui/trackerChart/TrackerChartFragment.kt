package makes.flint.alt.ui.constraintui.trackerChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.factories.TrackerBarChartFactory

/**
 * TrackerChartFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerChartFragment : BaseFragment(), TrackerChartContractView {

    private lateinit var views: TrackerChartViewHolder
    private lateinit var trackerChartPresenter: TrackerChartContractPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_coin_detail_chart, container, false)
        view?.let {
            views = TrackerChartViewHolder(view)
        }
        trackerChartPresenter = getPresenterComponent().provideTrackerChartPresenter()
        trackerChartPresenter.attachView(this)
        attachPresenter(trackerChartPresenter)
        trackerChartPresenter.initialise()
        return view
    }

    override fun displayTrackerEntriesChart(trackerListItems: List<TrackerListItem>) {
        val factory = TrackerBarChartFactory()
        val chart = factory.makeChart(context, trackerListItems)
        views.chartHolder.removeAllViews()
        views.chartHolder.addView(chart)
    }

    override fun showNoTrackerEntriesMessage() {
    }
}