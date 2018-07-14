package makes.flint.alt.ui.constraintui.trackerBarChart

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.trackerListItem.TrackerListItem

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
