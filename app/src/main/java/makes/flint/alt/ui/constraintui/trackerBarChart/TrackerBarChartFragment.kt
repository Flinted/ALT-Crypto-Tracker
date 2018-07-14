package makes.flint.alt.ui.constraintui.trackerBarChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.factories.trackerBarChartFactory.TRACKER_ITEM_LIMIT
import makes.flint.alt.factories.trackerBarChartFactory.TrackerBarChartFactory

/**
 * TrackerBarChartFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerBarChartFragment : BaseFragment(), TrackerBarChartContractView {

    private lateinit var views: TrackerBarChartViewHolder
    private lateinit var trackerChartPresenter: TrackerBarChartContractPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tracker_chart, container, false)
        views = TrackerBarChartViewHolder(view)
        trackerChartPresenter = getPresenterComponent().provideTrackerChartPresenter()
        trackerChartPresenter.attachView(this)
        attachPresenter(trackerChartPresenter)
        trackerChartPresenter.initialise()
        return view
    }

    override fun displayTrackerEntriesChart(trackerListItems: List<TrackerListItem>) {
        views.noEntriesMessage.visibility = View.GONE
        val factory = TrackerBarChartFactory()
        val chart = factory.makeChart(requireContext(), trackerListItems)
        displayMoreItemsIconIfRequired(trackerListItems.count())
        views.chartHolder.removeAllViews()
        views.chartHolder.addView(chart)
    }

    private fun displayMoreItemsIconIfRequired(count: Int) {
        if (count <= TRACKER_ITEM_LIMIT) {
            views.moreIndicator.visibility = View.GONE
            return
        }
        views.moreIndicator.visibility = View.VISIBLE
    }

    override fun showNoTrackerEntriesMessage() {
        views.noEntriesMessage.visibility = View.VISIBLE
    }
}