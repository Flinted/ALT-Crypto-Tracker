package did.chris.alt.ui.constraintui.coinlist

import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import did.chris.alt.R
import did.chris.alt.ui.search.SearchSummaryView


class CoinListViewHolder(view: View) {

    // Properties
    internal var coinList: RecyclerView = view.findViewById(R.id.coin_list_recycler_view)
    internal var coinSearchBar: SearchSummaryView = view.findViewById(R.id.coin_list_search_bar)
    internal var swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.coin_list_refresh_layout)
    internal var goToTopFAB: FloatingActionButton = view.findViewById(R.id.coin_list_FAB)
}
