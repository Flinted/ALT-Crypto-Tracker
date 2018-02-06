package makes.flint.poh.ui.market.coinDetail

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.response.histoResponse.HistoricalDataResponse

/**
 * CoinDetailContract
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
interface CoinDetailContractView : BaseContractView {
    fun displayCoinDetail(coin: CoinListItem?)
    fun displayHistoricalDataResponse(result: HistoricalDataResponse, chartType: Int)
    fun initialiseChangeChartButton()
}

interface CoinDetailContractPresenter : BaseContractPresenter<CoinDetailContractView> {
    fun initialise(coinSymbol: String)
    fun getCurrentHistoricalDataResponse(): HistoricalDataResponse
}