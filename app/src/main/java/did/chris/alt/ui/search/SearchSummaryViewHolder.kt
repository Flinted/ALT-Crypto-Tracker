package did.chris.alt.ui.search

import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import did.chris.alt.R

class SearchSummaryViewHolder(view: View) {

    // Properties
    internal val coinSearch: EditText = view.findViewById(R.id.coin_list_search_input)
    internal val marketSummary: ConstraintLayout = view.findViewById(R.id.market_summary_container)
    internal val marketTotalCap: TextView = view.findViewById(R.id.market_summary_marketcap)
    internal val marketVolume: TextView = view.findViewById(R.id.market_summary_volume)
    internal val marketCount: TextView = view.findViewById(R.id.market_summary_count)
    internal val marketChange1d: TextView = view.findViewById(R.id.market_summary_1d)
    internal val marketChange1w: TextView = view.findViewById(R.id.market_summary_1w)
    internal val coinSearchButton: ImageView = view.findViewById(R.id.coin_list_search_button)
    internal val coinSearchCancelButton: ImageView =
        view.findViewById(R.id.coin_list_search_cancel_button)
}
