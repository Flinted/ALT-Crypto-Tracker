package did.chris.alt.ui.constraintui.trackerSummary

import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import did.chris.alt.R

/**
 * PortfolioSummaryFragmentViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class PortfolioSummaryFragmentViewHolder(view: View) {

    internal var initialValue: TextView = view.findViewById(R.id.summary_initial_value)
    internal var currentValueUSD: TextView = view.findViewById(R.id.summary_current_value_USD)
    internal var currentValueBTC: TextView = view.findViewById(R.id.summary_current_value_BTC)
    internal var changePercentage: TextView = view.findViewById(R.id.summary_change_percentage)
    internal var summaryFAB: FloatingActionButton = view.findViewById(R.id.summary_fab)
    internal var settingsFAB: FloatingActionButton = view.findViewById(R.id.settings_fab)
    internal var progressSpinner: ProgressBar = view.findViewById(R.id.tracker_fab_progress_spinner)
}
