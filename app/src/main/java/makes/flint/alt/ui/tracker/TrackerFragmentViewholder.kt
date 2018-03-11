package makes.flint.alt.ui.tracker

import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import makes.flint.alt.R

/**
 * TrackerFragmentViewholder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerFragmentViewholder(view: View) {

    internal var trackerRecycler: RecyclerView = view.findViewById(R.id.tracker_recycler_view)
    internal var addCoinButton: FloatingActionButton = view.findViewById(R.id.tracker_FAB)
    internal var swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.tracker_refresh_layout)
}