package did.chris.alt.ui.constraintui.trackerBarChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import did.chris.alt.R
import did.chris.alt.base.BaseFragment
import did.chris.alt.data.trackerListItem.TrackerListItem
import did.chris.alt.factories.trackerBarChartFactory.TrackerBarChartFactory


class TrackerBarChartFragment : BaseFragment(), TrackerBarChartContractView {

    // Properties
    private lateinit var views: TrackerBarChartViewHolder
    private lateinit var trackerChartPresenter: TrackerBarChartContractPresenter

    // Lifecycle
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

    // Overrides
    override fun displayTrackerEntriesChart(trackerListItems: List<TrackerListItem>) {
        views.noEntriesMessage.visibility = View.GONE
        val factory = TrackerBarChartFactory()
        val chart = factory.makeChart(requireContext(), trackerListItems)
        views.chartHolder.removeAllViews()
        views.chartHolder.addView(chart)
    }

    override fun showMoreItemsIndicator() {
        views.moreIndicator.visibility = View.VISIBLE
    }

    override fun hideMoreItemsIndicator() {
        views.moreIndicator.visibility = View.GONE
    }

    override fun showNoTrackerEntriesMessage() {
        views.noEntriesMessage.visibility = View.VISIBLE
    }
}
