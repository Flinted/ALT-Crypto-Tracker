package makes.flint.poh.ui.market.coinDetail

import android.util.SparseArray
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.poh.data.response.histoResponse.HistoricalDataResponse
import makes.flint.poh.data.response.histoResponse.HistoricalDataUnitResponse
import javax.inject.Inject

/**
 * CoinDetailPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */

// Minute Resolution
const val CHART_1H = 0
const val CHART_6H = 1
const val CHART_24H = 2

// Hour Resolution
const val CHART_3D = 3
const val CHART_7D = 4

// Day Resolution
const val CHART_30D = 5
const val CHART_90D = 6
const val CHART_1Y = 7
const val CHART_ALL = 8

// API Resolutions
const val MINUTE_DATA = 0
const val HOUR_DATA = 1
const val DAY_DATA = 2

class CoinDetailPresenter @Inject constructor(private var dataController: DataController) : CoinDetailContractPresenter {

    private var coinDetailDialog: CoinDetailContractView? = null
    private lateinit var coinSymbol: String
    private val chartData: SparseArray<HistoricalDataResponse> = SparseArray()
    private var currentUnit = HOUR_DATA
    private var chartType = BAR_CHART

    override fun initialise() {
    }

    override fun initialise(coinSymbol: String) {
        this.coinSymbol = coinSymbol
    }

    override fun getHistoricalDataFor(chartResolution: Int) {
        val callback = makeHistoricalDataCallback()
        coinDetailDialog?.showLoading()
        val apiResolution = getAPIResolutionForRequestedChartType(chartResolution)
        getDataForResolution(apiResolution, chartResolution, callback)
    }

    override fun getCurrentHistoricalDataResponse(): HistoricalDataResponse? {
        return chartData[currentUnit]
    }

    private fun getDataForResolution(apiResolution: Int,
                                     chartResolution: Int,
                                     callback: RepositoryCallbackSingle<HistoricalDataResponse?>) {
        val cachedData = chartData[apiResolution] ?: let {
            dataController.getHistoricalDataFor(callback, coinSymbol, apiResolution, chartResolution)
            return
        }
        val dataToDisplay = prepareDataFor(chartResolution, cachedData)
        coinDetailDialog?.displayHistoricalDataResponse(dataToDisplay, chartType)
    }

    private fun makeHistoricalDataCallback(): RepositoryCallbackSingle<HistoricalDataResponse?> {
        return object : RepositoryCallbackSingle<HistoricalDataResponse?> {
            override fun onError(error: Throwable) {}
            override fun onRetrieve(refreshed: Boolean, apiResolution: Int, chartResolution: Int, result:
            HistoricalDataResponse?) {
                val coin = dataController.getCoinForSymbol(coinSymbol)
                coinDetailDialog?.displayCoinDetail(coin)
                coinDetailDialog?.initialiseChangeChartButton()
                result?.let {
                    chartData.put(apiResolution, result)
                    val dataToDisplay = prepareDataFor(chartResolution, result)
                    coinDetailDialog?.displayHistoricalDataResponse(dataToDisplay, chartType)
                }
            }
        }
    }

    private fun prepareDataFor(chartResolution: Int, result: HistoricalDataResponse): Array<HistoricalDataUnitResponse> {
        val invertedData = result.data
//        val subset = when (chartResolution) {
//            CHART_1H -> invertedData.copyOfRange(0, 59)
//            CHART_6h -> invertedData.copyOfRange(0, 359)
//            CHART_24H -> invertedData
//            CHART_3D -> invertedData.copyOfRange(0, 71)
//            CHART_7D -> invertedData
//            CHART_30D -> invertedData.copyOfRange(0, 29)
//            CHART_90D -> invertedData.copyOfRange(0, 89)
//            CHART_1Y -> invertedData.copyOfRange(0, 364)
//            else -> invertedData
//        }
        return invertedData
    }

    private fun getAPIResolutionForRequestedChartType(chartType: Int): Int {
        return when (chartType) {
            CHART_1H, CHART_6H, CHART_24H -> MINUTE_DATA
            CHART_3D, CHART_7D -> HOUR_DATA
            else -> DAY_DATA
        }
    }

    override fun detachView() {
        this.coinDetailDialog = null
    }

    override fun attachView(view: CoinDetailContractView) {
        this.coinDetailDialog = view
    }
}
