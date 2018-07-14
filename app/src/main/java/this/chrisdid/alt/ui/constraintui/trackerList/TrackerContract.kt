package this.chrisdid.alt.ui.constraintui.trackerList

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView
import this.chrisdid.alt.data.response.marketSummary.MarketSummaryResponse

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