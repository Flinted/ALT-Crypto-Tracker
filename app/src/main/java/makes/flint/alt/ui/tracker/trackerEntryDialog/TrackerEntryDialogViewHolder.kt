package makes.flint.alt.ui.tracker.trackerEntryDialog

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import makes.flint.alt.R

/**
 * TrackerEntryDialogViewHolder
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerEntryDialogViewHolder(view: View) {

    internal var deleteButton: ImageView = view.findViewById(R.id.dialog_tracker_delete)
    internal var coinName: TextView = view.findViewById(R.id.dialog_tracker_coin_name)
    internal var coinSymbol: TextView = view.findViewById(R.id.dialog_tracker_coin_symbol)
    internal var currentAmount: TextView = view.findViewById(R.id.dialog_tracker_number_owned)
    internal var currentValueUSD: TextView = view.findViewById(R.id.dialog_tracker_current_value_USD)
    internal var currentValueBTC: TextView = view.findViewById(R.id.dialog_tracker_current_value_BTC)
    internal var percentageChange: TextView = view.findViewById(R.id.dialog_tracker_percentage_change)
    internal var transactionsList: RecyclerView = view.findViewById(R.id.dialog_tracker_transactions_recycler)
}
