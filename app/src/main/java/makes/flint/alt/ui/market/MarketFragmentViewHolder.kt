package makes.flint.alt.ui.market

import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import makes.flint.alt.R
import makes.flint.alt.ui.interfaces.FragmentViewHolder

/**
 * MarketFragmentViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class MarketFragmentViewHolder(view: View) : FragmentViewHolder {

    internal var coinListRecyclerView: RecyclerView = view.findViewById(R.id.market_recycler_view)
    internal var progressSpinner: ProgressBar = view.findViewById(R.id.market_progress_spinner)
    internal var swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.coin_list_refresh_layout)
    internal var goToTopFAB: FloatingActionButton = view.findViewById(R.id.coin_list_FAB)
}