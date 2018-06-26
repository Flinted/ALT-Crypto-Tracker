package makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse

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