package makes.flint.alt.ui.constraintui.trackerList

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import makes.flint.alt.R
import makes.flint.alt.ui.search.SearchSummaryView

class TrackerFragmentViewholder(view: View) {

    internal var trackerRecycler: RecyclerView = view.findViewById(R.id.tracker_recycler_view)
    internal var swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.tracker_refresh_layout)
    internal var summarySearchBar: SearchSummaryView = view.findViewById(R.id.tracker_summary_search_bar)
    internal var noEntriesMessage: TextView = view.findViewById(R.id.tracker_list_no_entries_message)
}