package makes.flint.alt.ui.tracker.addCoinDialog

import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import makes.flint.alt.R

/**
 * AddCoinDialogViewHolder
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class AddCoinDialogViewHolder(view: View) {

    internal var assetSearch: AutoCompleteTextView = view.findViewById(R.id.asset_search)
    internal var selectedAsset: TextView = view.findViewById(R.id.selected_coin)
    internal var exchangeInput: EditText = view.findViewById(R.id.exchange_used)
    internal var quantityInput: EditText = view.findViewById(R.id.quantity_purchased)
    internal var priceInput: EditText = view.findViewById(R.id.cost_per_item)
    internal var notesInput: EditText = view.findViewById(R.id.transaction_notes)
    internal var dateInput: TextView = view.findViewById(R.id.date_of_purchase)
    internal var calendarButton: ImageView = view.findViewById(R.id.transaction_date_select)
    internal var currentPriceDisplay: TextView = view.findViewById(R.id.value_current)
    internal var purchasePriceDisplay: TextView = view.findViewById(R.id.value_at_purchase)
    internal var feesInput: EditText = view.findViewById(R.id.transaction_fees)
    internal var addEntryFAB: FloatingActionButton = view.findViewById(R.id.add_coin_fab)
}