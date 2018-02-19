package makes.flint.alt.ui.market.coinDetail

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse

/**
 * CoinDetailContract
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
interface CoinDetailContractView : BaseContractView {
    fun displayCoinDetail(coin: CoinListItem?)
    fun initialiseChangeChartButton()
    fun initialiseDataSelectListener()
    fun displayCandleChart(dataToDisplay: Array<HistoricalDataUnitResponse>)
    fun displayBarChart(dataToDisplay: Array<HistoricalDataUnitResponse>)
    fun displayLineChart(dataToDisplay: Array<HistoricalDataUnitResponse>)
    fun initialiseAdBanner()
}

interface CoinDetailContractPresenter : BaseContractPresenter<CoinDetailContractView> {
    fun initialise(coinSymbol: String)
    fun getHistoricalDataFor(chartResolution: Int)
    fun changeChartClicked()
}