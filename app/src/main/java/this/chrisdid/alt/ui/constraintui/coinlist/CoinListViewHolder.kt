package this.chrisdid.alt.ui.constraintui.coinlist

import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import this.chrisdid.alt.R
import this.chrisdid.alt.ui.search.SearchSummaryView

/**
 * CoinListViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class CoinListViewHolder(view: View) {
    internal var coinList: RecyclerView = view.findViewById(R.id.coin_list_recycler_view)
//    internal var coinToSearch: EditText = view.findViewById(R.id.coin_list_search_input)
    internal var coinSearchBar: SearchSummaryView = view.findViewById(R.id.coin_list_search_bar)
//    internal var marketSummary: ConstraintLayout = view.findViewById(R.id.market_summary_container)
//    internal var marketTotalCap: TextView = view.findViewById(R.id.market_summary_marketcap)
//    internal var marketVolume: TextView = view.findViewById(R.id.market_summary_volume)
//    internal var marketCount: TextView = view.findViewById(R.id.market_summary_count)
//    internal var marketChange1d: TextView = view.findViewById(R.id.market_summary_1d)
//    internal var marketChange1w: TextView = view.findViewById(R.id.market_summary_1w)
//    internal var coinSearchButton: ImageView = view.findViewById(R.id.coin_list_search_button)
//    internal var coinSearchCancelButton: ImageView =
//        view.findViewById(R.id.coin_list_search_cancel_button)
    internal var swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.coin_list_refresh_layout)
    internal var goToTopFAB: FloatingActionButton = view.findViewById(R.id.coin_list_FAB)
}