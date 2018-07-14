package this.chrisdid.alt.ui.constraintui.trackerBarChart

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView
import this.chrisdid.alt.data.trackerListItem.TrackerListItem

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
