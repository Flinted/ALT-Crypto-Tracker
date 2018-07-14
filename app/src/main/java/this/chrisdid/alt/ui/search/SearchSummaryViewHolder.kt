package this.chrisdid.alt.ui.search

import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import this.chrisdid.alt.R

/**
 * SearchSummaryViewHolder
 */
class SearchSummaryViewHolder(view: View) {
    internal var coinSearch: EditText = view.findViewById(R.id.coin_list_search_input)
    internal var marketSummary: ConstraintLayout = view.findViewById(R.id.market_summary_container)
    internal var marketTotalCap: TextView = view.findViewById(R.id.market_summary_marketcap)
    internal var marketVolume: TextView = view.findViewById(R.id.market_summary_volume)
    internal var marketCount: TextView = view.findViewById(R.id.market_summary_count)
    internal var marketChange1d: TextView = view.findViewById(R.id.market_summary_1d)
    internal var marketChange1w: TextView = view.findViewById(R.id.market_summary_1w)
    internal var coinSearchButton: ImageView = view.findViewById(R.id.coin_list_search_button)
    internal var coinSearchCancelButton: ImageView =
        view.findViewById(R.id.coin_list_search_cancel_button)
}
