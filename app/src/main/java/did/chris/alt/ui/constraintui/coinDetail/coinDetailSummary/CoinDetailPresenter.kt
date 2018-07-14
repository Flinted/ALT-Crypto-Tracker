package did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
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
