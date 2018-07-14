package this.chrisdid.alt.ui.constraintui.coinDetail.coinDetailChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import this.chrisdid.alt.R
import this.chrisdid.alt.base.BaseFragment
import this.chrisdid.alt.data.response.histoResponse.HistoricalDataUnitResponse
import this.chrisdid.alt.errors.ErrorHandler
import this.chrisdid.alt.factories.HistoricalDataChartFactory
import this.chrisdid.alt.ui.constraintui.coinDetail.coinDetailSummary.COIN_SYMBOL_KEY
import this.chrisdid.alt.utility.NumberFormatter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.math.BigDecimal

/**
 * CoinDetailChart
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

class CoinDetailChart : BaseFragment(), CoinDetailChartContractView {

    private lateinit var views: CoinDetailChartViewHolder
    private lateinit var coinDetailChartPresenter: CoinDetailChartContractPresenter

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
        coinDetailChartPresenter.initialise(coinSymbol)
        return view
    }

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
            hideHighlightedData()
            coinDetailChartPresenter.getHistoricalDataFor(chartResolution)
        }
    }

    private fun addHighlightListener(
        chart: LineChart,
        currentData: Array<HistoricalDataUnitResponse>
    ) {
        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(entry: Entry?, highlight: Highlight?) {
                val index = entry?.x ?: return
                val dataPoint = currentData[index.toInt()]
                presentHighlightedData(dataPoint)
            }
        })
    }

    private fun presentHighlightedData(dataPoint: HistoricalDataUnitResponse) {
        val closeFloat = dataPoint.close ?: return
        val close = BigDecimal(closeFloat.toDouble())
        views.chartHighlightValue.text = NumberFormatter.formatCurrencyAutomaticDigit(close)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY ha")
        val time = dataPoint.time?.toLong() ?: return
        val zoneId = ZoneId.systemDefault()
        val instant = Instant.ofEpochSecond(time).atZone(zoneId)
        views.chartHighlightTimeStamp.text = formatter.format(instant)
        views.chartHighlightValue.visibility = View.VISIBLE
        views.chartHighlightTimeStamp.visibility = View.VISIBLE
    }

    private fun hideHighlightedData() {
        views.chartHighlightValue.visibility = View.GONE
        views.chartHighlightTimeStamp.visibility = View.GONE
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
}
