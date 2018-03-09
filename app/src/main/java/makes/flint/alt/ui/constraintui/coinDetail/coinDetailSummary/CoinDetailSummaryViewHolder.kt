package makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary

import android.view.View
import android.widget.TextView
import makes.flint.alt.R
import makes.flint.alt.ui.interfaces.FragmentViewHolder

/**
 * CoinDetailSummaryViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class CoinDetailSummaryViewHolder(view: View) : FragmentViewHolder {

    internal var coinName: TextView = view.findViewById(R.id.dialog_coin_detail_name)
    internal var coinSymbol: TextView = view.findViewById(R.id.dialog_coin_detail_symbol)
    internal var priceFiat: TextView = view.findViewById(R.id.dialog_coin_detail_price_fiat)
    internal var priceBTC: TextView = view.findViewById(R.id.dialog_coin_detail_price_btc)
    internal var priceBTCTitle: TextView = view.findViewById(R.id.dialog_coin_detail_price_btc)
    internal var priceBillionCoin: TextView = view.findViewById(R.id.dialog_coin_detail_billion_coin)
    internal var rank: TextView = view.findViewById(R.id.dialog_coin_detail_rank)
    internal var volume24H: TextView = view.findViewById(R.id.dialog_coin_detail_volume_24h)
    internal var supplyAvailable: TextView = view.findViewById(R.id.dialog_coin_detail_available_supply)
    internal var supplyTotal: TextView = view.findViewById(R.id.dialog_coin_detail_total_supply)
    internal var marketCap: TextView = view.findViewById(R.id.dialog_coin_detail_market_cap)
}
