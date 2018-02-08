package makes.flint.poh.ui.market.coinDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import makes.flint.poh.R
import makes.flint.poh.base.BaseDialogFragment
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.response.histoResponse.HistoricalDataResponse
import makes.flint.poh.errors.ErrorHandler
import makes.flint.poh.factories.HistoricalDataChartFactory

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

    private lateinit var coinName: TextView
    private lateinit var coinSymbol: TextView
    private lateinit var priceFiat: TextView
    private lateinit var priceBTC: TextView
    private lateinit var priceBTCTitle: TextView
    private lateinit var priceBillionCoin: TextView
    private lateinit var rank: TextView
    private lateinit var volume24H: TextView
    private lateinit var supplyAvailable: TextView
    private lateinit var supplyTotal: TextView
    private lateinit var marketCap: TextView
    private lateinit var chartHolder: FrameLayout
    private lateinit var changeChartButton: ImageView
    private lateinit var chartLoading: ProgressBar

    private lateinit var coinDetailPresenter: CoinDetailContractPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coinDetailPresenter = getPresenterComponent().provideCoinDetailPresenter()
        coinDetailPresenter.attachView(this)
        this.attachPresenter(coinDetailPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.dialog_coin_detail, container)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        bindViews(view)
        val coinSymbol = arguments.get(DIALOG_COIN_KEY) as String
        coinDetailPresenter.initialise(coinSymbol)
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    private fun bindViews(view: View) {
        this.coinName = view.findViewById(R.id.dialog_coin_detail_name)
        this.coinSymbol = view.findViewById(R.id.dialog_coin_detail_symbol)
        this.rank = view.findViewById(R.id.dialog_coin_detail_rank)
        this.priceFiat = view.findViewById(R.id.dialog_coin_detail_price_fiat)
        this.priceBTC = view.findViewById(R.id.dialog_coin_detail_price_btc)
        this.priceBTCTitle = view.findViewById(R.id.dialog_coin_detail_price_btc_title)
        this.priceBillionCoin = view.findViewById(R.id.dialog_coin_detail_billion_coin)
        this.volume24H = view.findViewById(R.id.dialog_coin_detail_volume_24h)
        this.supplyAvailable = view.findViewById(R.id.dialog_coin_detail_available_supply)
        this.supplyTotal = view.findViewById(R.id.dialog_coin_detail_total_supply)
        this.marketCap = view.findViewById(R.id.dialog_coin_detail_market_cap)
        this.chartHolder = view.findViewById(R.id.dialog_coin_detail_chart_holder)
        this.changeChartButton = view.findViewById(R.id.dialog_coin_detail_change_chart_button)
        this.chartLoading = view.findViewById(R.id.dialog_coin_detail_loading_spinner)
    }

    override fun displayHistoricalDataResponse(result: HistoricalDataResponse, chartType: Int) {
        val factory = HistoricalDataChartFactory(result.data, "Hours")
        val chart = when (chartType) {
            CANDLE_CHART -> factory.createCandleChart(chartHolder.context)
            BAR_CHART -> factory.createBarChart(chartHolder.context)
            else -> factory.createLineChart(chartHolder.context)
        }
        chartHolder.addView(chart)
    }

    override fun displayCoinDetail(coin: CoinListItem?) {
        this.coinName.text = coin?.name
        this.coinSymbol.text = coin?.symbolFormatted
        this.rank.text = coin?.rank.toString()
        this.priceFiat.text = coin?.priceData?.priceUSDFormatted
        this.priceBTC.text = coin?.priceData?.priceBTCFormatted
        this.priceBillionCoin.text = coin?.priceData?.stabilisedPrice
        this.volume24H.text = coin?.volume24HourFormatted
        this.supplyAvailable.text = coin?.availableSupplyFormatted
        this.supplyTotal.text = coin?.totalSupplyFormatted
        this.marketCap.text = coin?.marketCapFormatted()
        if (coin?.symbol == "DOGE") {
            this.priceBTCTitle.text = "Price(DOGE):"
            this.priceBTC.text = "1 DOGE"
        }
    }

    override fun initialiseChangeChartButton() {
        changeChartButton.setOnClickListener {
            val currentData = coinDetailPresenter.getCurrentHistoricalDataResponse()
            when (chartHolder.getChildAt(0)) {
                is LineChart -> displayHistoricalDataResponse(currentData, BAR_CHART)
                is BarChart -> displayHistoricalDataResponse(currentData, CANDLE_CHART)
                else -> displayHistoricalDataResponse(currentData, LINE_CHART)
            }
            chartHolder.removeViewAt(0)
        }
    }

    override fun showLoading() {
        chartLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        chartLoading.visibility = View.GONE
    }

    override fun showError(stringId: Int?) {
        ErrorHandler.showError(activity, stringId)
    }
}
