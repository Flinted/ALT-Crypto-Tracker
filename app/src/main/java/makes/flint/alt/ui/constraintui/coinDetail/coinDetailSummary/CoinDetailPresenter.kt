package makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import javax.inject.Inject

/**
 * CoinDetailPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

class CoinDetailPresenter @Inject constructor(private var dataController: DataController)
    : BasePresenter<CoinDetailContractView>(), CoinDetailContractPresenter {

    // Properties

    private lateinit var coinSymbol: String

    // Lifecycle

    override fun initialise() {
    }

    override fun initialise(coinSymbol: String) {
        this.coinSymbol = coinSymbol
        val coin = dataController.getCoinForSymbol(coinSymbol)
        view?.displayCoinDetail(coin)
        view?.initialiseDYORButton(coin)
    }
}
