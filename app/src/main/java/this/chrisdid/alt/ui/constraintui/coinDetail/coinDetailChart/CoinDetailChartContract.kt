package this.chrisdid.alt.ui.constraintui.coinDetail.coinDetailChart

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView
import this.chrisdid.alt.data.response.histoResponse.HistoricalDataUnitResponse

/**
 * CoinDetailChartContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

interface CoinDetailChartContractView : BaseContractView {
    fun displayChart(currentData: Array<HistoricalDataUnitResponse>)
    fun setChartChangeListener()
}

interface CoinDetailChartContractPresenter : BaseContractPresenter<CoinDetailChartContractView> {
    fun initialise(coinSymbol: String)
    fun getHistoricalDataFor(chartResolution: Int)
}