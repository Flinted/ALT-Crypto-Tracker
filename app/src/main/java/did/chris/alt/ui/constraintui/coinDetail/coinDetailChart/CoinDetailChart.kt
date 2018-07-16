package did.chris.alt.ui.constraintui.coinDetail.coinDetailChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import did.chris.alt.R
import did.chris.alt.base.BaseFragment
import did.chris.alt.data.response.histoResponse.HistoricalDataUnitResponse
import did.chris.alt.errors.ErrorHandler
import did.chris.alt.factories.HistoricalDataChartFactory
import did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary.COIN_SYMBOL_KEY

class CoinDetailChart : BaseFragment(), CoinDetailChartContractView {

    // Properties
    private lateinit var views: CoinDetailChartViewHolder
    private lateinit var coinDetailChartPresenter: CoinDetailChartContractPresenter

    // Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coin_detail_chart, container, false)
        views = CoinDetailChartViewHolder(view)
        coinDetailChartPresenter = getPresenterComponent().provideCoinChartPresenter()
        coinDetailChartPresenter.attachView(this)
        attachPresenter(coinDetailChartPresenter)
        val coinSymbol = arguments?.get(COIN_SYMBOL_KEY) as String
        coinDetailChartPresenter.setCoinSymbol(coinSymbol)
        coinDetailChartPresenter.initialise()
        return view
    }

    // Overrides
    override fun displayChart(currentData: Array<HistoricalDataUnitResponse>) {
        val factory = HistoricalDataChartFactory(currentData, "Data")
        val chart = factory.createLineChart(views.chartHolder.context) ?: let {
            hideLoading()
            showError(ErrorHandler.GENERAL_ERROR)
            return
        }
        hideLoading()
        views.chartHolder.removeAllViews()
        views.chartHolder.addView(chart)
        addHighlightListener(chart, currentData)
    }

    override fun setChartChangeListener() {
        views.chartSelectButtons.setOnCheckedChangeListener { _, selected ->
            val chartResolution = when (selected) {
                R.id.coin_detail_radio_1H  -> CHART_1H
                R.id.coin_detail_radio_6H  -> CHART_6H
                R.id.coin_detail_radio_1D  -> CHART_24H
                R.id.coin_detail_radio_1W  -> CHART_7D
                R.id.coin_detail_radio_1M  -> CHART_30D
                R.id.coin_detail_radio_1Y  -> CHART_1Y
                R.id.coin_detail_radio_all -> CHART_ALL
                else                       -> CHART_24H
            }
            coinDetailChartPresenter.getHistoricalDataFor(chartResolution)
        }
    }

    override fun hideLoading() {
        super.hideLoading()

        views.progressBar.visibility = View.GONE
        views.chartSelectButtons.visibility = View.VISIBLE
    }

    override fun showLoading() {
        super.showLoading()

        views.progressBar.visibility = View.VISIBLE
        views.chartSelectButtons.visibility = View.INVISIBLE
    }

    override fun displayHighlightTime(formattedTime: String?) {
        views.chartHighlightTimeStamp.text = formattedTime
        views.chartHighlightTimeStamp.visibility = View.VISIBLE
    }

    override fun displayHighlightValue(formattedAmount: String) {
        views.chartHighlightValue.text = formattedAmount
        views.chartHighlightValue.visibility = View.VISIBLE
    }

    override fun hideHighlightedData() {
        views.chartHighlightValue.visibility = View.GONE
        views.chartHighlightTimeStamp.visibility = View.GONE
    }

    // Private Functions
    private fun addHighlightListener(
        chart: LineChart,
        currentData: Array<HistoricalDataUnitResponse>
    ) {
        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {}

            override fun onValueSelected(entry: Entry?, highlight: Highlight?) {
                val index = entry?.x ?: return
                val dataPoint = currentData[index.toInt()]
                coinDetailChartPresenter.valueSelected(dataPoint)
            }
        })
    }
}
