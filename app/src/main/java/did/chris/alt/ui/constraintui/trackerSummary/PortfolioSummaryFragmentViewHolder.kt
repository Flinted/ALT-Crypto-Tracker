package did.chris.alt.ui.constraintui.trackerSummary

import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import did.chris.alt.R

class PortfolioSummaryFragmentViewHolder(view: View) {

    // Properties
    internal val initialValue: TextView = view.findViewById(R.id.summary_initial_value)
    internal val currentValueUSD: TextView = view.findViewById(R.id.summary_current_value_USD)
    internal val currentValueBTC: TextView = view.findViewById(R.id.summary_current_value_BTC)
    internal val changePercentage: TextView = view.findViewById(R.id.summary_change_percentage)
    internal val summaryFAB: FloatingActionButton = view.findViewById(R.id.summary_fab)
    internal val settingsFAB: FloatingActionButton = view.findViewById(R.id.settings_fab)
    internal val progressSpinner: ProgressBar = view.findViewById(R.id.tracker_fab_progress_spinner)
}
