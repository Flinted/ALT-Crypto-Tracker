package makes.flint.alt.ui.tracker

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView

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
    fun hideProgressSpinner()
}

interface TrackerContractPresenter : BaseContractPresenter<TrackerContractView> {
    fun refreshCache()
    fun onDestroy()
    fun refreshTrackerEntries()
}