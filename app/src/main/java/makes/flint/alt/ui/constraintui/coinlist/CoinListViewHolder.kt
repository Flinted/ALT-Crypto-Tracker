package makes.flint.alt.ui.constraintui.coinlist

import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import makes.flint.alt.R

/**
 * CoinListViewHolder
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class CoinListViewHolder(view: View) {
    internal var coinList: RecyclerView = view.findViewById(R.id.coin_list_recycler_view)
    internal var coinSearch: EditText = view.findViewById(R.id.coin_list_search_input)
    internal var swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.coin_list_refresh_layout)
    internal var goToTopFAB: FloatingActionButton = view.findViewById(R.id.coin_list_FAB)
}