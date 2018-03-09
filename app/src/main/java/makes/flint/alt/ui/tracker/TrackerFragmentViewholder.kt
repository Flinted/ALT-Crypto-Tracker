package makes.flint.alt.ui.tracker

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import makes.flint.alt.R
import makes.flint.alt.ui.tracker.summary.SummaryViewPager

/**
 * TrackerFragmentViewholder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerFragmentViewholder(view: View) {

    internal var progressSpinner: ProgressBar = view.findViewById(R.id.tracker_progress_spinner)
    internal var trackerConstraint: ConstraintLayout = view.findViewById(R.id.tracker_main_content)
    internal var trackerRecycler: RecyclerView = view.findViewById(R.id.tracker_recycler_view)
    internal var summaryViewPager: SummaryViewPager = view.findViewById(R.id.tracker_chart_area)
    internal var addCoinButton: FloatingActionButton = view.findViewById(R.id.tracker_FAB)
    internal var swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.tracker_refresh_layout)
    internal var noEntriesPlaceHolder: ConstraintLayout = view.findViewById(R.id.tracker_no_entries_placeholder)
    internal var changeChartButton: ImageView = view.findViewById(R.id.tracker_show_chart_button)
    internal var snapShotButton: ImageView = view.findViewById(R.id.tracker_show_snapshot_button)
    internal var cancelChartButton: ImageView = view.findViewById(R.id.tracker_show_portfolio_button)
    internal var hideSummaryAreaButton: ImageView = view.findViewById(R.id.tracker_hide_summary_area_button)
    internal var showSummaryAreaButton: ImageView = view.findViewById(R.id.tracker_show_summary_area_button)

    internal val originalConstraint = ConstraintSet()
    internal val noChartConstraint = ConstraintSet()
    internal val chartViewConstraint = ConstraintSet()

    init {
        originalConstraint.clone(trackerConstraint)
        noChartConstraint.clone(trackerConstraint)
        chartViewConstraint.clone(trackerConstraint)
        configureNoChartConstraint()
        configureChartViewConstraint()
    }

    private fun configureNoChartConstraint() {
        noChartConstraint.setVisibility(R.id.tracker_chart_area, View.GONE)
        noChartConstraint.setVisibility(R.id.tracker_hide_summary_area_button, View.GONE)
        noChartConstraint.setVisibility(R.id.tracker_show_summary_area_button, View.VISIBLE)
        noChartConstraint.connect(R.id.tracker_recycler_view, ConstraintSet.TOP, R.id.tracker_show_summary_area_button,
                ConstraintSet.BOTTOM)
    }

    private fun configureChartViewConstraint() {
        chartViewConstraint.connect(
                R.id.tracker_chart_area,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM)
        chartViewConstraint.constrainHeight(R.id.tracker_chart_area, ViewGroup.LayoutParams.MATCH_PARENT)
        chartViewConstraint.setVisibility(R.id.tracker_show_chart_button, View.GONE)
        chartViewConstraint.setVisibility(R.id.tracker_show_snapshot_button, View.GONE)
        chartViewConstraint.setVisibility(R.id.tracker_show_portfolio_button, View.VISIBLE)
    }
}