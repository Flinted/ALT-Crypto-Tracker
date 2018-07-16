package did.chris.alt.ui.constraintui.trackerList

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse

interface TrackerContractView : BaseContractView {

    // Functions
    fun initialiseTrackerList()
    fun showNoTrackerEntriesMessage()
    fun initialiseTrackerListListeners()
    fun initialiseRefreshListener()
    fun hideProgressSpinner()
    fun initialiseSearchBar()
    fun displayMarketSummary(marketSummary: MarketSummaryResponse?)
    fun initialiseAddEntryButton()
    fun hideNoTrackerEntriesMessage()
}

interface TrackerContractPresenter : BaseContractPresenter<TrackerContractView> {

    // Functions
    fun refreshCache()
    fun onDestroy()
    fun refreshTrackerEntries()
    fun handleTrackerEntriesChange(noEntriesPresent: Boolean)
}