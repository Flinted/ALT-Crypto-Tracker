package makes.flint.alt.ui.tracker.summary.summaryFragments

import android.view.View
import android.widget.TextView
import makes.flint.alt.R

/**
 * SummaryFragmentViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class SummaryFragmentViewHolder(view: View) {

    internal var initialValue: TextView = view.findViewById(R.id.summary_initial_value)
    internal var currentValueUSD: TextView = view.findViewById(R.id.summary_current_value_USD)
    internal var currentValueBTC: TextView = view.findViewById(R.id.summary_current_value_BTC)
    internal var changePercentage: TextView = view.findViewById(R.id.summary_change_percentage)
    internal var amountSpent: TextView = view.findViewById(R.id.summary_amount_spent)
    internal var amountSold: TextView = view.findViewById(R.id.summary_amount_sold)
}
