package makes.flint.poh.ui.market.coinDetail

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.response.histoResponse.HistoricalDataResponse
import makes.flint.poh.data.response.histoResponse.HistoricalDataUnitResponse

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