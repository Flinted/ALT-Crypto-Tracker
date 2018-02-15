package makes.flint.alt.ui.market.coinDetail

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.response.histoResponse.HistoricalDataResponse
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse

/**
 * CoinDetailContract
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
interface CoinDetailContractView : BaseContractView {
    fun displayCoinDetail(coin: CoinListItem?)
    fun displayHistoricalDataResponse(result: Array<HistoricalDataUnitResponse>, chartType: Int)
    fun initialiseChangeChartButton()
}

interface CoinDetailContractPresenter : BaseContractPresenter<CoinDetailContractView> {
    fun initialise(coinSymbol: String)
    fun getHistoricalDataFor(chartResolution: Int)
    fun getCurrentHistoricalDataResponse(): HistoricalDataResponse?
}