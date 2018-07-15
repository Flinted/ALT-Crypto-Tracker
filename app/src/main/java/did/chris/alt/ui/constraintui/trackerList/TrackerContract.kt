package did.chris.alt.ui.constraintui.trackerList

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse

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
    fun initialiseAddEntryButton()
}

interface TrackerContractPresenter : BaseContractPresenter<TrackerContractView> {
    fun refreshCache()
    fun onDestroy()
    fun refreshTrackerEntries()
}