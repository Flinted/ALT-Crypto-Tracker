package did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary

import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import did.chris.alt.R
import did.chris.alt.ui.interfaces.FragmentViewHolder

class CoinDetailSummaryViewHolder(view: View) : FragmentViewHolder {

    // Properties
    internal var coinName: TextView = view.findViewById(R.id.dialog_coin_detail_name)
    internal var coinSymbol: TextView = view.findViewById(R.id.dialog_coin_detail_symbol)
    internal var priceFiat: TextView = view.findViewById(R.id.dialog_coin_detail_price_fiat)
    internal var priceBTC: TextView = view.findViewById(R.id.dialog_coin_detail_price_btc)
    internal var priceBTCTitle: TextView = view.findViewById(R.id.dialog_coin_detail_price_btc)
    internal var priceBillionCoin: TextView = view.findViewById(R.id.dialog_coin_detail_billion_coin)
    internal var rank: TextView = view.findViewById(R.id.dialog_coin_detail_rank)
    internal var sortedRank: TextView = view.findViewById(R.id.dialog_coin_detail_sorted_rank)
    internal var sortedRankTitle: TextView = view.findViewById(R.id.dialog_coin_detail_sorted_rank_title)
    internal var volume24H: TextView = view.findViewById(R.id.dialog_coin_detail_volume_24h)
    internal var supplyAvailable: TextView = view.findViewById(R.id.dialog_coin_detail_available_supply)
    internal var supplyTotal: TextView = view.findViewById(R.id.dialog_coin_detail_total_supply)
    internal var marketCap: TextView = view.findViewById(R.id.dialog_coin_detail_market_cap)
    internal var mainContent: ConstraintLayout = view.findViewById(R.id.dialog_coin_detail_main_content)
    internal var dyorButton: TextView = view.findViewById(R.id.dialog_coin_detail_DYOR)
    internal var backButton: ImageView = view.findViewById(R.id.back_button)
    internal var addEntryButton: ImageView = view.findViewById(R.id.dialog_coin_detail_add_entry)
}
