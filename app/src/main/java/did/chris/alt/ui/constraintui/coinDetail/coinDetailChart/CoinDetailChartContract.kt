package did.chris.alt.ui.constraintui.coinDetail.coinDetailChart

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.response.histoResponse.HistoricalDataUnitResponse

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