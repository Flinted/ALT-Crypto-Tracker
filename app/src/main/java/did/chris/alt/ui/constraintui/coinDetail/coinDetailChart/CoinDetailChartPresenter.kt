package did.chris.alt.ui.constraintui.coinDetail.coinDetailChart

import android.util.SparseArray
import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
import did.chris.alt.data.dataController.callbacks.RepositoryCallbackSingle
import did.chris.alt.data.response.histoResponse.HistoricalDataResponse
import did.chris.alt.data.response.histoResponse.HistoricalDataUnitResponse
import did.chris.alt.errors.ErrorHandler
import did.chris.alt.utility.DateFormatter
import did.chris.alt.utility.NumberFormatter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import java.math.BigDecimal
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

    override fun setCoinSymbol(coinSymbol: String) {
        this.coinSymbol = coinSymbol
    }

    override fun initialise() {
        this.coinSymbol = coinSymbol
        view?.setChartChangeListener()
        getHistoricalDataFor(CHART_24H)
    }

    override fun getHistoricalDataFor(chartResolution: Int) {
        val callback = makeHistoricalDataCallback()
        val apiResolution = getAPIResolutionForRequestedChartType(chartResolution)
        view?.hideHighlightedData()
        getDataForResolution(apiResolution, chartResolution, callback)
    }

    override fun valueSelected(dataPoint: HistoricalDataUnitResponse) {
        val closeFloat = dataPoint.close ?: return
        formatValueForHighlight(closeFloat)
        val time = dataPoint.time?.toLong() ?: return
        formatTimeForHighlight(time)
    }

    // Private Functions
    private fun formatTimeForHighlight(time: Long) {
        val zoneId = ZoneId.systemDefault()
        val instant = Instant.ofEpochSecond(time).atZone(zoneId)
        val formattedTime = DateFormatter.DATE_HOUR_AMPM.format(instant)
        view?.displayHighlightTime(formattedTime)
    }

    private fun formatValueForHighlight(closeFloat: Float) {
        val close = BigDecimal(closeFloat.toDouble())
        val formattedAmount = NumberFormatter.formatCurrencyAutomaticDigit(close)
        view?.displayHighlightValue(formattedAmount)
    }

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