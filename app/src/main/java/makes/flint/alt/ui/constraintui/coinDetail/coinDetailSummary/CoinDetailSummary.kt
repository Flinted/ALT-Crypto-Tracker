package makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.errors.ErrorHandler

/**
 * CoinDetailSummary
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
const val DIALOG_COIN_KEY = "DialogCoinKey"

class CoinDetailSummary : BaseFragment(), CoinDetailContractView {

    // Static Initializer

    companion object {
        fun getInstanceFor(coinSymbol: String): CoinDetailSummary {
            val coinDetail = CoinDetailSummary()
            val bundle = Bundle()
            bundle.putString(DIALOG_COIN_KEY, coinSymbol)
            coinDetail.arguments = bundle
            return coinDetail
        }
    }

    // Properties

    private lateinit var coinDetailPresenter: CoinDetailContractPresenter
    private lateinit var views: CoinDetailSummaryViewHolder

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coinDetailPresenter = getPresenterComponent().provideCoinDetailPresenter()
        coinDetailPresenter.attachView(this)
        this.attachPresenter(coinDetailPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_coin_detail, container, false)
        view?.let {
            this.views = CoinDetailSummaryViewHolder(view)
        }
        val coinSymbol = arguments.get(DIALOG_COIN_KEY) as String
        coinDetailPresenter.initialise(coinSymbol)
        return view
    }

    // Overrides

    override fun displayCoinDetail(coin: CoinListItem?) {
        views.coinName.text = coin?.name
        views.coinSymbol.text = coin?.symbolFormatted
        views.rank.text = coin?.rank.toString()
        views.priceFiat.text =
                getString(R.string.title_priceUSD, coin?.priceData?.priceUSDFormatted)
        views.priceBTC.text =
                getString(R.string.title_priceBTC, coin?.priceData?.priceBTCFormatted)
        views.priceBillionCoin.text =
                getString(R.string.dialog_coinDetail_one_billion_coin, coin?.priceData?.stabilisedPrice)
        views.volume24H.text = coin?.volume24HourFormatted
        views.supplyAvailable.text = coin?.availableSupplyFormatted
        views.supplyTotal.text = coin?.totalSupplyFormatted
        views.marketCap.text = coin?.marketCapFormatted()
        checkDogeMuchWow(coin?.symbol)
    }

    override fun showError(stringId: Int?) = ErrorHandler.showError(activity, stringId)

    // Private Functions

    private fun checkDogeMuchWow(coinSymbol: String?) {
        if (coinSymbol == "DOGE") {
            views.priceBTCTitle.text = getString(R.string.dialog_coinDetail_title_doge_price)
            views.priceBTC.text = getString(R.string.dialog_coinDetail_placeholder_doge_price)
        }
    }
}
