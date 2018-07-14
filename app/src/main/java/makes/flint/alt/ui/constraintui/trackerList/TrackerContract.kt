package makes.flint.alt.ui.constraintui.trackerList

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse

/**
 * TrackerContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface TrackerContractView : BaseContractView {
    fun initialiseTrackerList()
    fun showNoTrackerEntriesMessage()
    fun initialiseTrackerListListeners()
    fun initialiseRefreshListener()
    fun hideProgressSpinner()
    fun initialiseSearchBar()
    fun displayMarketSummary(marketSummary: MarketSummaryResponse?)
}

interface TrackerContractPresenter : BaseContractPresenter<TrackerContractView> {
    fun refreshCache()
    fun onDestroy()
    fun refreshTrackerEntries()
}