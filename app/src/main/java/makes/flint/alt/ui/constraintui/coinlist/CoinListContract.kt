package makes.flint.alt.ui.constraintui.coinlist

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse

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