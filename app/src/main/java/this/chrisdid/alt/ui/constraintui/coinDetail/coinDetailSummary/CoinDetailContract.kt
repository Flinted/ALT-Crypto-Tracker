package this.chrisdid.alt.ui.constraintui.coinDetail.coinDetailSummary

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView
import this.chrisdid.alt.data.coinListItem.CoinListItem

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