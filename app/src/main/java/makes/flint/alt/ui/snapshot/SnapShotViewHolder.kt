package makes.flint.alt.ui.snapshot

import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import makes.flint.alt.R

/**
 * SnapShotViewHolder
 * Copyright © 2018 Flint Makes. All rights reserved.
 */

internal class SnapShotViewHolder(activity: SnapShotActivity) {

    internal val valueBTC: TextView = activity.findViewById(R.id.activity_snapshot_value_btc)
    internal val valueUSD: TextView = activity.findViewById(R.id.activity_snapshot_value_usd)
    internal val summaryChart: LineChart = activity.findViewById(R.id.activity_snapshot_chart)
}