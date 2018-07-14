package did.chris.alt.ui.tracker.trackerEntryDialog.transactionsList

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import did.chris.alt.R

/**
 * TransactionsListViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TransactionsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var date: TextView = itemView.findViewById(R.id.transaction_item_date)
    var exchange: TextView = itemView.findViewById(R.id.transaction_item_exchange)
    var quantity: TextView = itemView.findViewById(R.id.transaction_item_quantity)
    var pricePaid: TextView = itemView.findViewById(R.id.transaction_item_price_paid)
    var totalCost: TextView = itemView.findViewById(R.id.transaction_item_total_cost)
    var notes: TextView = itemView.findViewById(R.id.transaction_item_notes)
    var deleteButton: ImageView = itemView.findViewById(R.id.transaction_item_delete)
}