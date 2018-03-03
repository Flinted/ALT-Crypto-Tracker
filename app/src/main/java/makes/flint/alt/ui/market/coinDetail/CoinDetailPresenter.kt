package makes.flint.alt.ui.market.coinDetail

import android.util.SparseArray
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.alt.data.response.histoResponse.HistoricalDataResponse
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse
import javax.inject.Inject

/**
 * CoinDetailPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */

// Hour Resolution
const val CHART_24H = 2
const val CHART_7D = 3

// Day Resolution
const val CHART_30D = 4
const val CHART_90D = 5
const val CHART_180D = 6
const val CHART_1Y = 7
const val CHART_ALL = 8

// API Resolutions
const val MINUTE_DATA = 0
const val HOUR_DATA = 1
const val DAY_DATA = 2

class CoinDetailPresenter @Inject constructor(private var dataController: DataController) :
        CoinDetailContractPresenter {

    // Properties

    private var coinDetailDialog: CoinDetailContractView? = null
    private lateinit var coinSymbol: String
    private val chartData: SparseArray<HistoricalDataResponse> = SparseArray()
    private var currentData: Array<HistoricalDataUnitResponse> = arrayOf()
    private var currentChartType = LINE_CHART

    // Lifecycle

    override fun initialise() {
    }

    override fun initialise(coinSymbol: String) {
        this.coinSymbol = coinSymbol
        val coin = dataController.getCoinForSymbol(coinSymbol)
        coinDetailDialog?.displayCoinDetail(coin)
        coinDetailDialog?.initialiseChangeChartButton()
        coinDetailDialog?.initialiseDataSelectListener()
    }

    override fun detachView() {
        this.coinDetailDialog = null
    }

    override fun attachView(view: CoinDetailContractView) {
        this.coinDetailDialog = view
    }

    // Overrides

    override fun changeChartClicked() {
        val newChartType = when (currentChartType) {
            BAR_CHART -> LINE_CHART
            LINE_CHART -> CANDLE_CHART
            else -> BAR_CHART
        }
        currentChartType = newChartType
        displayChartForCurrentConfiguration()
    }

    override fun getHistoricalDataFor(chartResolution: Int) {
        val callback = makeHistoricalDataCallback()
        coinDetailDialog?.showLoading()
        val apiResolution = getAPIResolutionForRequestedChartType(chartResolution)
        getDataForResolution(apiResolution, chartResolution, callback)
    }

    // Private Functions

    private fun getDataForResolution(apiResolution: Int,
                                     chartResolution: Int,
                                     callback: RepositoryCallbackSingle<HistoricalDataResponse?>) {
        val cachedData = chartData[apiResolution] ?: let {
            coinDetailDialog?.showLoading()
            dataController.getHistoricalDataFor(callback, coinSymbol, apiResolution, chartResolution)
            return
        }
        currentData = prepareDataFor(chartResolution, cachedData)
        displayChartForCurrentConfiguration()
    }

    private fun makeHistoricalDataCallback(): RepositoryCallbackSingle<HistoricalDataResponse?> {
        return object : RepositoryCallbackSingle<HistoricalDataResponse?> {
            override fun onError(error: Throwable) {
                coinDetailDialog?.hideLoading()
            }

            override fun onRetrieve(refreshed: Boolean, apiResolution: Int, chartResolution: Int, result:
            HistoricalDataResponse?) {
                coinDetailDialog?.hideLoading()
                result ?: return
                chartData.put(apiResolution, result)
                currentData = prepareDataFor(chartResolution, result)
                displayChartForCurrentConfiguration()
            }
        }
    }

    private fun displayChartForCurrentConfiguration() {
        when (currentChartType) {
            CANDLE_CHART -> coinDetailDialog?.displayCandleChart(currentData)
            BAR_CHART -> coinDetailDialog?.displayBarChart(currentData)
            else -> coinDetailDialog?.displayLineChart(currentData)
        }
    }

    private fun prepareDataFor(chartResolution: Int, result: HistoricalDataResponse): Array<HistoricalDataUnitResponse> {
        val invertedData = result.data.reversedArray()
        val subset = when (chartResolution) {
            CHART_24H -> invertedData.copyOfRange(0, 24)
            CHART_7D -> invertedData
            CHART_30D -> invertedData.copyOfRange(0, 29)
            CHART_90D -> invertedData.copyOfRange(0, 89)
            CHART_180D -> invertedData.copyOfRange(0, 179)
            CHART_1Y -> invertedData.copyOfRange(0, 364)
            else -> invertedData
        }
        return subset.reversedArray()
    }

    private fun getAPIResolutionForRequestedChartType(chartType: Int): Int {
        return when (chartType) {
            CHART_30D, CHART_90D, CHART_180D, CHART_1Y -> DAY_DATA
            else -> HOUR_DATA
        }
    }
}
