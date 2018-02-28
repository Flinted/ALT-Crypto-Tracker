package makes.flint.alt.ui.market.coinDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseDialogFragment
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.factories.HistoricalDataChartFactory

/**
 * CoinDetailDialog
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
const val DIALOG_COIN_KEY = "DialogCoinKey"
const val LINE_CHART = 0
const val BAR_CHART = 1
const val CANDLE_CHART = 2

class CoinDetailDialog : BaseDialogFragment(), CoinDetailContractView {
    companion object {
        fun getInstanceFor(coinSymbol: String): CoinDetailDialog {
            val coinDetail = CoinDetailDialog()
            val bundle = Bundle()
            bundle.putString(DIALOG_COIN_KEY, coinSymbol)
            coinDetail.arguments = bundle
            return coinDetail
        }
    }

    private lateinit var coinDetailPresenter: CoinDetailContractPresenter
    private lateinit var views: CoinDetailDialogViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coinDetailPresenter = getPresenterComponent().provideCoinDetailPresenter()
        coinDetailPresenter.attachView(this)
        this.attachPresenter(coinDetailPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialog_coin_detail, container)
        view?.let {
            this.views = CoinDetailDialogViewHolder(view)
        }
        val coinSymbol = arguments.get(DIALOG_COIN_KEY) as String
        coinDetailPresenter.initialise(coinSymbol)
        coinDetailPresenter.getHistoricalDataFor(CHART_24H)
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun displayBarChart(dataToDisplay: Array<HistoricalDataUnitResponse>) {
        val factory = HistoricalDataChartFactory(dataToDisplay, "Data")
        val chart = factory.createBarChart(views.chartHolder.context) ?: let {
            hideLoading()
            showError(ErrorHandler.GENERAL_ERROR)
            return
        }
        hideLoading()
        views.chartHolder.addView(chart)
    }

    override fun displayLineChart(dataToDisplay: Array<HistoricalDataUnitResponse>) {
        val factory = HistoricalDataChartFactory(dataToDisplay, "Data")
        val chart = factory.createLineChart(views.chartHolder.context) ?: let {
            hideLoading()
            showError(ErrorHandler.GENERAL_ERROR)
            return
        }
        hideLoading()
        views.chartHolder.addView(chart)
    }

    override fun displayCandleChart(dataToDisplay: Array<HistoricalDataUnitResponse>) {
        val factory = HistoricalDataChartFactory(dataToDisplay, "Data")
        val chart = factory.createCandleChart(views.chartHolder.context) ?: let {
            hideLoading()
            showError(ErrorHandler.GENERAL_ERROR)
            return
        }
        hideLoading()
        views.chartHolder.addView(chart)
    }

    override fun displayCoinDetail(coin: CoinListItem?) {
        views.coinName.text = coin?.name
        views.coinSymbol.text = coin?.symbolFormatted
        views.rank.text = coin?.rank.toString()
        views.priceFiat.text = coin?.priceData?.priceUSDFormatted
        views.priceBTC.text = coin?.priceData?.priceBTCFormatted
        views.priceBillionCoin.text = coin?.priceData?.stabilisedPrice
        views.volume24H.text = coin?.volume24HourFormatted
        views.supplyAvailable.text = coin?.availableSupplyFormatted
        views.supplyTotal.text = coin?.totalSupplyFormatted
        views.marketCap.text = coin?.marketCapFormatted()
        checkDogeMuchWow(coin?.symbol)
    }

    private fun checkDogeMuchWow(coinSymbol: String?) {
        if (coinSymbol == "DOGE") {
            views.priceBTCTitle.text = getString(R.string.dialog_coin_detail_doge_price_title)
            views.priceBTC.text = getString(R.string.dialog_coin_detail_doge_price)
        }
    }

    override fun initialiseDataSelectListener() {
        views.selector.setOnCheckedChangeListener { _, selectedId ->
            val dataResolution = when (selectedId) {
                R.id.dialog_coin_detail_selector_24Hours -> CHART_24H
                R.id.dialog_coin_detail_selector_7Days -> CHART_7D
                R.id.dialog_coin_detail_selector_30Days -> CHART_30D
                R.id.dialog_coin_detail_selector_90Days -> CHART_90D
                R.id.dialog_coin_detail_selector_180Days -> CHART_180D
                else -> CHART_1Y
            }
            coinDetailPresenter.getHistoricalDataFor(dataResolution)
        }
    }

    override fun initialiseChangeChartButton() {
        views.changeChartButton.setOnClickListener {
            coinDetailPresenter.changeChartClicked()
            views.chartHolder.removeViewAt(0)
        }
    }

    override fun showLoading() {
        views.chartLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        views.chartLoading.visibility = View.GONE
    }

    override fun showError(stringId: Int?) = ErrorHandler.showError(activity, stringId)
}
