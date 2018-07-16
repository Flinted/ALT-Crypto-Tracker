package did.chris.alt.ui.constraintui.coinlist

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse

interface CoinListContractView : BaseContractView {

    // Functions
    fun initialiseListAdapter()
    fun initialiseSwipeRefreshListener()
    fun initialiseScrollListener()
    fun initialiseFABonClick()
    fun showDialogForCoin(coinSymbol: String)
    fun initialiseSearchOnClick()
    fun displayMarketSummary(marketSummaryResponse: MarketSummaryResponse?)
    fun hideGoToTopFAB()
    fun showGoToTopFAB()
}

interface CoinListContractPresenter : BaseContractPresenter<CoinListContractView> {

    // Functions
    fun refresh()
    fun onCoinSelected(coinSymbol: String)
    fun onDestroy()
    fun assessScrollChange(yPosition: Int)
}
