package makes.flint.alt.ui.constraintui.trackerChart

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import makes.flint.alt.R

/**
 * TrackerChartViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerChartViewHolder(view: View) {

    internal var chartHolder: FrameLayout = view.findViewById(R.id.tracker_chart_holder)
    internal var noEntriesMessage: TextView = view.findViewById(R.id.tracker_no_entries_message)
    internal var moreIndicator: ImageView = view.findViewById(R.id.tracker_chart_more_indicator)
}