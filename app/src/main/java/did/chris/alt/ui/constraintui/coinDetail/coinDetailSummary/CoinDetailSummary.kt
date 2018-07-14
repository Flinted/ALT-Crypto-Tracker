package did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import did.chris.alt.R
import did.chris.alt.base.BaseFragment
import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.data.coinListItem.CoinListItem
import did.chris.alt.errors.ErrorHandler
import java.net.URLEncoder


/**
 * CoinDetailSummary
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
const val COIN_SYMBOL_KEY = "CoinSymbolKey"

class CoinDetailSummary : BaseFragment(), CoinDetailContractView {

    // Static Initializer

    companion object {
        fun getInstanceFor(coinSymbol: String): CoinDetailSummary {
            val coinDetail = CoinDetailSummary()
            val bundle = Bundle()
            bundle.putString(COIN_SYMBOL_KEY, coinSymbol)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coin_detail, container, false)
        this.views = CoinDetailSummaryViewHolder(view)
        val coinSymbol = arguments?.get(COIN_SYMBOL_KEY) as String
        coinDetailPresenter.initialise(coinSymbol)
        return view
    }

    // Overrides

    override fun displayCoinDetail(coin: CoinListItem?) {
        views.coinName.text = coin?.name
        views.coinSymbol.text = coin?.symbolFormatted
        views.rank.text = coin?.rank.toString()
        views.sortedRankTitle.text =
                getString(
                    R.string.dialog_coinDetail_title_sorted_rank,
                    context?.resources?.getStringArray(R.array.sort_options)?.get(
                        ALTSharedPreferences.getSort()
                    )
                )
        views.sortedRank.text = coin?.sortedRank.toString()
        views.priceFiat.text =
                getString(R.string.title_priceUSD, coin?.priceData?.priceUSDFormatted)
        views.priceBTC.text =
                getString(R.string.title_priceBTC, coin?.priceData?.priceBTCFormatted)
        views.priceBillionCoin.text =
                getString(
                    R.string.dialog_coinDetail_one_billion_coin,
                    coin?.priceData?.stabilisedPrice
                )
        views.volume24H.text = coin?.volume24HourFormatted
        views.supplyAvailable.text = coin?.availableSupplyFormatted
        views.supplyTotal.text = coin?.totalSupplyFormatted
        views.marketCap.text = coin?.marketCapFormatted()
        checkDogeMuchWow(coin?.symbol)
    }

    override fun initialiseDYORButton(coin: CoinListItem?) {
        views.dyorButton.setOnClickListener {
            val queryString = "Cryptocurrency ${coin?.name} information"
            val query = URLEncoder.encode(queryString, "utf-8")
            val url = "http://www.google.com/search?q=$query"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
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
