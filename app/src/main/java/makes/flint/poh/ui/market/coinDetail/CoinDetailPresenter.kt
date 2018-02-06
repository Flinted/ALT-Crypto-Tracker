package makes.flint.poh.ui.market.coinDetail

import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.poh.data.response.histoResponse.HistoricalDataResponse
import javax.inject.Inject

/**
 * CoinDetailPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinDetailPresenter @Inject constructor(private var dataController: DataController) : CoinDetailContractPresenter {

    private var coinDetail: CoinDetailContractView? = null
    private lateinit var coinSymbol: String
    private lateinit var historicalDataResponse: HistoricalDataResponse

    override fun initialise() {
    }

    override fun initialise(coinSymbol: String) {
        this.coinSymbol = coinSymbol
        val callback = makeHistoricalDataCallback()
        coinDetail?.showLoading()
        dataController.getHistoricalDataFor(callback, coinSymbol)
    }

    private fun makeHistoricalDataCallback(): RepositoryCallbackSingle<HistoricalDataResponse?> {
        return object : RepositoryCallbackSingle<HistoricalDataResponse?> {
            override fun onError(error: Throwable) {}
            override fun onRetrieve(refreshed: Boolean, lastSync: String, result: HistoricalDataResponse?) {
                val coin = dataController.getCoinForSymbol(coinSymbol)
                coinDetail?.displayCoinDetail(coin)
                coinDetail?.initialiseChangeChartButton()
                result?.let {
                    coinDetail?.hideLoading()
                    historicalDataResponse = result
                    coinDetail?.displayHistoricalDataResponse(result, LINE_CHART)
                }
            }
        }
    }

    override fun getCurrentHistoricalDataResponse() = historicalDataResponse

    override fun detachView() {
        this.coinDetail = null
    }

    override fun attachView(view: CoinDetailContractView) {
        this.coinDetail = view
    }
}
