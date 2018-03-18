package makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.factories.HistoricalDataChartFactory
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary.COIN_SYMBOL_KEY
import makes.flint.alt.utility.NumberFormatter
import java.math.BigDecimal

/**
 * CoinDetailChart
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

class CoinDetailChart : BaseFragment(), CoinDetailChartContractView {

    private lateinit var views: CoinDetailChartViewHolder
    private lateinit var coinDetailChartPresenter: CoinDetailChartContractPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_coin_detail_chart, container, false)
        view?.let {
            views = CoinDetailChartViewHolder(view)
        }
        coinDetailChartPresenter = getPresenterComponent().provideCoinChartPresenter()
        coinDetailChartPresenter.attachView(this)
        attachPresenter(coinDetailChartPresenter)
        val coinSymbol = arguments.get(COIN_SYMBOL_KEY) as String
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
        views.chartHolder.addView(chart)
        addHighlightListener(chart, currentData)
    }

    private fun addHighlightListener(chart: LineChart, currentData: Array<HistoricalDataUnitResponse>) {
        chart.setOnChartValueSelectedListener(object:OnChartValueSelectedListener{
            override fun onNothingSelected() {
                hideHighLightedData()
            }

            override fun onValueSelected(entry: Entry?, highlight: Highlight?) {
                val index = entry?.x ?: return
                val dataPoint = currentData[index.toInt()]
                presentHighlightedData(dataPoint)
            }
        })
    }

    private fun hideHighLightedData() {
        views.chartHighlight.visibility = View.INVISIBLE
    }

    private fun presentHighlightedData(dataPoint: HistoricalDataUnitResponse) {
        val closeFloat = dataPoint.close ?: return
        val close = BigDecimal(closeFloat.toDouble())
        views.chartHighlight.text = NumberFormatter.formatCurrency(close)
        views.chartHighlight.visibility = View.VISIBLE
    }
}
