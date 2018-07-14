package did.chris.alt.ui.constraintui.trackerBarChart

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.trackerListItem.TrackerListItem

/**
 * TrackerChartContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface TrackerBarChartContractView: BaseContractView {
    fun displayTrackerEntriesChart(trackerListItems: List<TrackerListItem>)
    fun showNoTrackerEntriesMessage()
}

interface TrackerBarChartContractPresenter: BaseContractPresenter<TrackerBarChartContractView> {
}
