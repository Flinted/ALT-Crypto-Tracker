package makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import javax.inject.Inject

/**
 * CoinDetailPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

// Hour Resolution
const val CHART_24H = 2
const val CHART_7D = 3

// Day Resolution
const val CHART_30D = 4
const val CHART_90D = 5
const val CHART_180D = 6
const val CHART_1Y = 7
const val CHART_ALL = 8

// API Resolutions
const val MINUTE_DATA = 0
const val HOUR_DATA = 1
const val DAY_DATA = 2

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
    }
}
