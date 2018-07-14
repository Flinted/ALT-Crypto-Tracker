package makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart

import android.util.SparseArray
import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.alt.data.response.histoResponse.HistoricalDataResponse
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse
import makes.flint.alt.errors.ErrorHandler
import javax.inject.Inject

// Minute Resolution
const val CHART_1H = 0
const val CHART_6H = 1

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
const val MINUTE_DATA = -1
const val HOUR_DATA = 0
const val DAY_DATA = 1

class CoinDetailChartPresenter @Inject constructor(private val dataController: DataController) :
    BasePresenter<CoinDetailChartContractView>(), CoinDetailChartContractPresenter {

    private lateinit var coinSymbol: String
    private val chartData: SparseArray<HistoricalDataResponse> = SparseArray()
    private var currentData: Array<HistoricalDataUnitResponse> = arrayOf()

    override fun initialise() {
    }

    override fun initialise(coinSymbol: String) {
        this.coinSymbol = coinSymbol
        view?.setChartChangeListener()
        getHistoricalDataFor(CHART_24H)
    }

    override fun getHistoricalDataFor(chartResolution: Int) {
        val callback = makeHistoricalDataCallback()
        val apiResolution = getAPIResolutionForRequestedChartType(chartResolution)
        getDataForResolution(apiResolution, chartResolution, callback)
    }

    // Private Functions

    private fun getDataForResolution(
        apiResolution: Int,
        chartResolution: Int,
        callback: RepositoryCallbackSingle<HistoricalDataResponse?>
    ) {
        val cachedData = chartData[apiResolution] ?: let {
            view?.showLoading()
            dataController.getHistoricalDataFor(
                callback,
                coinSymbol,
                apiResolution,
                chartResolution
            )
            return
        }
        currentData = prepareDataFor(chartResolution, cachedData)
        displayChartForCurrentConfiguration()
    }

    private fun makeHistoricalDataCallback(): RepositoryCallbackSingle<HistoricalDataResponse?> {
        return object : RepositoryCallbackSingle<HistoricalDataResponse?> {
            override fun onError(error: Throwable) {
                view?.hideLoading()
                view?.showError(ErrorHandler.API_FAILURE)
            }

            override fun onRetrieve(
                refreshed: Boolean, apiResolution: Int, chartResolution: Int, result:
                HistoricalDataResponse?
            ) {
                view?.hideLoading()
                result ?: return
                chartData.put(apiResolution, result)
                currentData = prepareDataFor(chartResolution, result)
                displayChartForCurrentConfiguration()
            }
        }
    }

    private fun displayChartForCurrentConfiguration() {
        view?.displayChart(currentData)
    }

    private fun prepareDataFor(
        chartResolution: Int,
        result: HistoricalDataResponse
    ): Array<HistoricalDataUnitResponse> {
        val invertedData = result.data.reversedArray()
        val subset = when (chartResolution) {
            CHART_1H   -> invertedData.copyOfRange(0, 59)
            CHART_6H   -> invertedData.copyOfRange(0, 359)
            CHART_24H  -> invertedData.copyOfRange(0, 24)
            CHART_7D   -> invertedData.copyOfRange(0, 163)
            CHART_30D  -> invertedData.copyOfRange(0, 29)
            CHART_90D  -> invertedData.copyOfRange(0, 89)
            CHART_180D -> invertedData.copyOfRange(0, 179)
            CHART_1Y   -> invertedData.copyOfRange(0, 364)
            else       -> invertedData
        }
        return subset.reversedArray()
    }

    private fun getAPIResolutionForRequestedChartType(chartType: Int): Int {
        return when (chartType) {
            CHART_1H, CHART_6H                                    -> MINUTE_DATA
            CHART_30D, CHART_90D, CHART_180D, CHART_1Y, CHART_ALL -> DAY_DATA
            else                                                  -> HOUR_DATA
        }
    }
}