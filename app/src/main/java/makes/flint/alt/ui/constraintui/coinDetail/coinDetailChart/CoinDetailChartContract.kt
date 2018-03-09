package makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse

/**
 * CoinDetailChartContract
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */

interface CoinDetailChartContractView : BaseContractView {
    fun displayChart(currentData: Array<HistoricalDataUnitResponse>)

}

interface CoinDetailChartContractPresenter : BaseContractPresenter<CoinDetailChartContractView> {
    fun initialise(coinSymbol: String)

}