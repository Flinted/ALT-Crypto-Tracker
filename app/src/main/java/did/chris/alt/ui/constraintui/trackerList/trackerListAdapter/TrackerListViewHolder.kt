package did.chris.alt.ui.constraintui.trackerList.trackerListAdapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import did.chris.alt.R

class TrackerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Properties
    internal val itemContent: ConstraintLayout = itemView.findViewById(R.id.tracker_item_content)
    internal val coinName: TextView = itemView.findViewById(R.id.dialog_coin_detail_name)
    internal val coinSymbol: TextView = itemView.findViewById(R.id.dialog_coin_detail_symbol)
    internal val currentPrice: TextView = itemView.findViewById(R.id.current_price_USD)
    internal val dollarCostAverage: TextView = itemView.findViewById(R.id.tracker_item_dca)
    internal val numberOwned: TextView = itemView.findViewById(R.id.number_owned)
    internal val currentValue: TextView = itemView.findViewById(R.id.value)
    internal val indicator: ImageView = itemView.findViewById(R.id.indicator)
    internal val currentProfit: TextView = itemView.findViewById(R.id.current_profit_change)

    // Functions
    internal fun getContext() = itemContent.context
}
