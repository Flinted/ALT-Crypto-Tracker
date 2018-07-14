package this.chrisdid.alt.ui.constraintui.trackerEntryDetail

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import this.chrisdid.alt.R

/**
 * TrackerDetailDialogViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerDetailDialogViewHolder(view: View) {

    internal var deleteButton: ImageView = view.findViewById(R.id.dialog_tracker_delete)
    internal var coinName: TextView = view.findViewById(R.id.dialog_tracker_coin_name)
    internal var coinSymbol: TextView = view.findViewById(R.id.dialog_tracker_coin_symbol)
    internal var currentAmount: TextView = view.findViewById(R.id.dialog_tracker_number_owned)
    internal var currentValueUSD: TextView = view.findViewById(R.id.dialog_tracker_current_value_USD)
    internal var currentValueBTC: TextView = view.findViewById(R.id.dialog_tracker_current_value_BTC)
    internal var percentageChange: TextView = view.findViewById(R.id.dialog_tracker_percentage_change)
    internal var transactionsList: RecyclerView = view.findViewById(R.id.dialog_tracker_transactions_recycler)
    internal var addAssetButton: FloatingActionButton = view.findViewById(R.id.dialog_tracker_add_coin_button)
}