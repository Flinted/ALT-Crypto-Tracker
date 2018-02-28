package makes.flint.alt.ui.snapshot

import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import makes.flint.alt.R

/**
 * SnapShotViewHolder
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */

internal class SnapShotViewHolder(activity: SnapShotFragment) {

    internal val valueBTC: TextView = activity.findViewById(R.id.fragment_snapshot_value_btc)
    internal val valueUSD: TextView = activity.findViewById(R.id.fragment_snapshot_value_usd)
    internal val summaryChart: LineChart = activity.findViewById(R.id.fragment_snapshot_chart)
}