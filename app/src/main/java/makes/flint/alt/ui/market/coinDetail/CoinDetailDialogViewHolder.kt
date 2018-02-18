package makes.flint.alt.ui.market.coinDetail

import android.view.View
import android.widget.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import makes.flint.alt.R
import makes.flint.alt.ui.interfaces.FragmentViewHolder

/**
 * CoinDetailDialogViewHolder
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class CoinDetailDialogViewHolder(view: View) : FragmentViewHolder {

    internal  var coinName: TextView = view.findViewById(R.id.dialog_coin_detail_name)
    internal  var coinSymbol: TextView = view.findViewById(R.id.dialog_coin_detail_symbol)
    internal  var priceFiat: TextView = view.findViewById(R.id.dialog_coin_detail_price_fiat)
    internal  var priceBTC: TextView = view.findViewById(R.id.dialog_coin_detail_price_btc)
    internal  var priceBTCTitle: TextView = view.findViewById(R.id.dialog_coin_detail_price_btc_title)
    internal  var priceBillionCoin: TextView = view.findViewById(R.id.dialog_coin_detail_billion_coin)
    internal  var rank: TextView = view.findViewById(R.id.dialog_coin_detail_rank)
    internal  var volume24H: TextView  = view.findViewById(R.id.dialog_coin_detail_volume_24h)
    internal  var supplyAvailable: TextView = view.findViewById(R.id.dialog_coin_detail_available_supply)
    internal  var supplyTotal: TextView = view.findViewById(R.id.dialog_coin_detail_total_supply)
    internal  var marketCap: TextView= view.findViewById(R.id.dialog_coin_detail_market_cap)
    internal  var chartHolder: FrameLayout = view.findViewById(R.id.dialog_coin_detail_chart_holder)
    internal  var changeChartButton: ImageView = view.findViewById(R.id.dialog_coin_detail_change_chart_button)
    internal  var chartLoading: ProgressBar = view.findViewById(R.id.dialog_coin_detail_loading_spinner)
    internal  var selector: RadioGroup = view.findViewById(R.id.dialog_coin_detail_chart_resolution_selector)
    private  var adBanner: AdView = view.findViewById(R.id.adView)

    init {
        val adRequest = AdRequest.Builder().build()
        adBanner.loadAd(adRequest)
    }
}