package did.chris.alt.ui.constraintui.trackerList.trackerListAdapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import did.chris.alt.R

/**
 * TrackerListViewHolder
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
class TrackerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val itemContent: ConstraintLayout = itemView.findViewById(R.id.tracker_item_content)
    val coinName: TextView = itemView.findViewById(R.id.dialog_coin_detail_name)
    val coinSymbol: TextView = itemView.findViewById(R.id.dialog_coin_detail_symbol)
    val currentPrice: TextView = itemView.findViewById(R.id.current_price_USD)
    val dollarCostAverage: TextView = itemView.findViewById(R.id.tracker_item_dca)
    val numberOwned: TextView = itemView.findViewById(R.id.number_owned)
    val currentValue: TextView = itemView.findViewById(R.id.value)
    val indicator: ImageView = itemView.findViewById(R.id.indicator)
    val currentProfit: TextView = itemView.findViewById(R.id.current_profit_change)
    fun getContext() = itemContent.context
}
