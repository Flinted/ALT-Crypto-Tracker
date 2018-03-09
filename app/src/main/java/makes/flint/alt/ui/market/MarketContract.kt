package makes.flint.alt.ui.market

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse

/**
 * MarketContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface MarketContractView : BaseContractView {
    fun initialiseListAdapter()
    fun initialiseSwipeRefreshListener()
    fun initialiseScrollListener()
    fun initialiseFABonClick()
    fun initialiseAdapterListeners()
    fun showDialogForCoin(coinSymbol: String)
    fun updateMarketSummary(marketSummary: MarketSummaryResponse)
    fun updateLastSyncTime(lastSync: String)
    fun hideProgressSpinner()
}

interface MarketContractPresenter : BaseContractPresenter<MarketContractView> {
    fun onCoinSelected(coinSymbol: String)
    fun refresh()
    fun onDestroy()
}