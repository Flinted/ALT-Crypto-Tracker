package did.chris.alt.ui.constraintui.trackerBarChart

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.trackerListItem.TrackerListItem

interface TrackerBarChartContractView: BaseContractView {

    // Functions
    fun displayTrackerEntriesChart(trackerListItems: List<TrackerListItem>)
    fun showNoTrackerEntriesMessage()
    fun showMoreItemsIndicator()
    fun hideMoreItemsIndicator()
}

interface TrackerBarChartContractPresenter: BaseContractPresenter<TrackerBarChartContractView> {
}
