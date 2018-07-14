package did.chris.alt.ui.constraintui.coinlist

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse

/**
 * CoinListContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface CoinListContractView : BaseContractView {
    fun initialiseListAdapter()
    fun initialiseSwipeRefreshListener()
    fun initialiseScrollListener()
    fun initialiseFABonClick()
    fun showDialogForCoin(coinSymbol: String)
    fun initialiseSearchOnClick()
    fun displayMarketSummary(marketSummaryResponse: MarketSummaryResponse?)
}

interface CoinListContractPresenter : BaseContractPresenter<CoinListContractView> {
    fun refresh()
    fun onCoinSelected(coinSymbol: String)
    fun onDestroy()

}