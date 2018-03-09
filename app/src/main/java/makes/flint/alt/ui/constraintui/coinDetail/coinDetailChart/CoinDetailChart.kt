package makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.factories.HistoricalDataChartFactory

/**
 * CoinDetailChart
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */

const val COIN_SYMBOL_CHART_KEY = "CoinSymbolChartKey"

class CoinDetailChart : BaseFragment(), CoinDetailChartContractView {

    // Static Initializer

    companion object {
        fun getInstanceFor(coinSymbol: String): CoinDetailChart {
            val coinDetail = CoinDetailChart()
            val bundle = Bundle()
            bundle.putString(COIN_SYMBOL_CHART_KEY, coinSymbol)
            coinDetail.arguments = bundle
            return coinDetail
        }
    }

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
        val coinSymbol = arguments.get(COIN_SYMBOL_CHART_KEY) as String
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
        views.chartHolder.addView(chart)    }
}
