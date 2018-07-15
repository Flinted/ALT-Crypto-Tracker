package did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.coinListItem.CoinListItem

interface CoinDetailContractView : BaseContractView {
    fun displayCoinDetail(coin: CoinListItem?)
    fun initialiseDYORButton(coin: CoinListItem?)
    fun initialiseAddEntryButton(coin: CoinListItem?)
}

interface CoinDetailContractPresenter : BaseContractPresenter<CoinDetailContractView> {
    fun setCoinSymbol(coinSymbol: String?)
}