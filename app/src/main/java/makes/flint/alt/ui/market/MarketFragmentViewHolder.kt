package makes.flint.alt.ui.market

import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import makes.flint.alt.R
import makes.flint.alt.ui.interfaces.FragmentViewHolder

/**
 * MarketFragmentViewHolder
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketFragmentViewHolder(view: View) : FragmentViewHolder {

    internal var coinListRecyclerView: RecyclerView = view.findViewById(R.id.market_recycler_view)
    internal var progressSpinner: ProgressBar = view.findViewById(R.id.market_progress_spinner)
    internal var swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.coin_list_refresh_layout)
    internal var goToTopFAB: FloatingActionButton = view.findViewById(R.id.coin_list_FAB)
    internal var lastSyncTime: TextView = view.findViewById(R.id.bottom_ticker_right)
    internal var currentSort: TextView = view.findViewById(R.id.bottom_ticker_left)
    internal var marketSummary: ConstraintLayout = view.findViewById(R.id.market_top_ticker)
    internal var marketCapTextView: TextView = view.findViewById(R.id.market_summary_market_cap)
    internal var vol24HTextView: TextView = view.findViewById(R.id.market_summary_vol_24H)
    internal var market1DTitleTextView: TextView = view.findViewById(R.id.market_summary_1D_title)
    internal var market1DValueTextView: TextView = view.findViewById(R.id.market_summary_1D)
    internal var market1WTitleTextView: TextView = view.findViewById(R.id.market_summary_1W_title)
    internal var market1WValueTextView: TextView = view.findViewById(R.id.market_summary_1W)
}