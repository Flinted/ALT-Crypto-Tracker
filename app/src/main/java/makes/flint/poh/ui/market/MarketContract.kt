package makes.flint.poh.ui.market

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView

/**
 * MarketContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface MarketContractView : BaseContractView {
    fun initialiseListAdapter()
    fun initialiseSwipeRefreshListener()
    fun initialiseScrollListener()
    fun initialiseFABonClick()
    fun initialiseAdapterListeners()
    fun showDialogForCoin(coinSymbol: String)
    fun updateMarketSummary(oneHour: String, twentyFourHour: String, sevenDay: String, coins: Int)
    fun updateLastSyncTime(lastSync: String?)
}

interface MarketContractPresenter : BaseContractPresenter<MarketContractView> {
    fun onCoinSelected(coinSymbol: String)
    fun refresh()
    fun onDestroy()
}