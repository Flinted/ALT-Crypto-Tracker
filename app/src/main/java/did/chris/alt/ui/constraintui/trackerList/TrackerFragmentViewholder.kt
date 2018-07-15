package did.chris.alt.ui.constraintui.trackerList

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import did.chris.alt.R
import did.chris.alt.ui.search.SearchSummaryView

class TrackerFragmentViewholder(view: View) {

    internal var trackerRecycler: RecyclerView = view.findViewById(R.id.tracker_recycler_view)
    internal var swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.tracker_refresh_layout)
    internal var summarySearchBar: SearchSummaryView = view.findViewById(R.id.tracker_summary_search_bar)
    internal var noEntriesMessage: TextView = view.findViewById(R.id.tracker_list_no_entries_message)
    internal var addButton: ImageView =view.findViewById(R.id.dialog_tracker_detail_add_entry)
    internal var backButton: ImageView =view.findViewById(R.id.back_button)
}