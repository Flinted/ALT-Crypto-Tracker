package did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.coinListItem.CoinListItem

/**
 * CoinDetailContract
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
interface CoinDetailContractView : BaseContractView {
    fun displayCoinDetail(coin: CoinListItem?)
    fun initialiseDYORButton(coin: CoinListItem?)
}

interface CoinDetailContractPresenter : BaseContractPresenter<CoinDetailContractView> {
    fun initialise(coinSymbol: String)
}