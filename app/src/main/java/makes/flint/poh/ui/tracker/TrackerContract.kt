package makes.flint.poh.ui.tracker

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView

/**
 * TrackerContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface TrackerContractView : BaseContractView {
    fun initialiseChartListeners()
    fun initialiseTrackerList()
    fun initialiseAddCoinButtonListener()
    fun initialiseConstraintSets()
    fun showNoTrackerEntriesMessage()
    fun initialiseTrackerListListeners()
    fun initialiseRefreshListener()
    fun initialiseSummaryPager()
}

interface TrackerContractPresenter : BaseContractPresenter<TrackerContractView> {
    fun refreshCache()
    fun onDestroy()
    fun refreshTrackerEntries()
}