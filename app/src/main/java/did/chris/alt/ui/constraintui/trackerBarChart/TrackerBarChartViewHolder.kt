package did.chris.alt.ui.constraintui.trackerBarChart

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import did.chris.alt.R

class TrackerBarChartViewHolder(view: View) {

    // Properties
    internal var chartHolder: FrameLayout = view.findViewById(R.id.tracker_chart_holder)
    internal var noEntriesMessage: TextView = view.findViewById(R.id.tracker_no_entries_message)
    internal var moreIndicator: ImageView = view.findViewById(R.id.tracker_chart_more_indicator)
}
