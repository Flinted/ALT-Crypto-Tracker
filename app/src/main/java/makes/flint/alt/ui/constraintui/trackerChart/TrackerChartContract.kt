package makes.flint.alt.ui.constraintui.trackerChart

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.trackerListItem.TrackerListItem

/**
 * TrackerChartContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface TrackerChartContractView: BaseContractView {
    fun displayTrackerEntriesChart(trackerListItems: List<TrackerListItem>)
    fun showNoTrackerEntriesMessage()
}

interface TrackerChartContractPresenter: BaseContractPresenter<TrackerChartContractView> {
}
