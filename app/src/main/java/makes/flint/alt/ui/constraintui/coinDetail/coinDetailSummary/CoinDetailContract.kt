package makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.coinListItem.CoinListItem

/**
 * CoinDetailContract
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
interface CoinDetailContractView : BaseContractView {
    fun displayCoinDetail(coin: CoinListItem?)
}

interface CoinDetailContractPresenter : BaseContractPresenter<CoinDetailContractView> {
    fun initialise(coinSymbol: String)
}