package did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.coinListItem.CoinListItem

interface CoinDetailContractView : BaseContractView {

    // Functions
    fun displayCoinDetail(coin: CoinListItem?)
    fun initialiseDYORButton(coin: String)
    fun initialiseAddEntryButton(coin: String)
}

interface CoinDetailContractPresenter : BaseContractPresenter<CoinDetailContractView> {

    // Functions
    fun setCoinSymbol(coinSymbol: String?)
}
