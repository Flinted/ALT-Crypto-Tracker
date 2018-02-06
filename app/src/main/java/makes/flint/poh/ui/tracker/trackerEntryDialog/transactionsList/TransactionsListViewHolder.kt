package makes.flint.poh.ui.tracker.trackerEntryDialog.transactionsList

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import makes.flint.poh.R

/**
 * TransactionsListViewHolder
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
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