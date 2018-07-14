package this.chrisdid.alt.ui.constraintui.coinlist.coinListAdapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import this.chrisdid.alt.R

/**
 * CoinListViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class CoinListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var card: ConstraintLayout = itemView.findViewById(R.id.main_card)
    var name: TextView = itemView.findViewById(R.id.coin_list_name)
    var ticker: TextView = itemView.findViewById(R.id.coin_ticker)
    var price: TextView = itemView.findViewById(R.id.coin_price)
    var favouriteIcon: ImageView = itemView.findViewById(R.id.coin_list_item_favourite_checkbox)
    var oneHourChange: TextView = itemView.findViewById(R.id.coin_1h_change)
    var twentyFourHourChange: TextView = itemView.findViewById(R.id.coin_1d_change)
    var sevenDayChange: TextView = itemView.findViewById(R.id.coin_1w_change)
    var oneHourIndicator: ImageView = itemView.findViewById(R.id.indicator_1h)
    var twentyFourHourIndicator: ImageView = itemView.findViewById(R.id.indicator_1d)
    var sevenDayIndicator: ImageView = itemView.findViewById(R.id.indicator_1w)
}
