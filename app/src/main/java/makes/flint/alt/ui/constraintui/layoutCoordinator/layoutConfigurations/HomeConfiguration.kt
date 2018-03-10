package makes.flint.alt.ui.constraintui.layoutCoordinator.layoutConfigurations

import android.content.Context
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import makes.flint.alt.R
import makes.flint.alt.ui.constraintui.coinlist.CoinListFragment
import makes.flint.alt.ui.constraintui.trackerChart.TrackerChartFragment
import makes.flint.alt.ui.constraintui.trackerSummary.SummaryFragment

/**
 * HomeConfiguration
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class HomeConfiguration(context: Context) : LayoutConfiguration {

    override fun frameTopChangeRequired(fragment: Fragment?): Pair<Int, SummaryFragment?> {
        val shouldChange = if (fragment is SummaryFragment) LAYOUT_NO_CHANGE else LAYOUT_CHANGE
        val summaryFragment = if(shouldChange == LAYOUT_CHANGE) SummaryFragment() else null
        return Pair(shouldChange, summaryFragment)
    }
    override fun frameCentreChangeRequired(fragment: Fragment?) : Pair<Int, TrackerChartFragment?> {
        val shouldChange = if (fragment is TrackerChartFragment) LAYOUT_NO_CHANGE else LAYOUT_CHANGE
        val trackerChartFragment = if(shouldChange == LAYOUT_CHANGE) TrackerChartFragment() else null
        return Pair(shouldChange, trackerChartFragment)
    }

    override fun frameBottomChangeRequired(fragment: Fragment?) : Pair<Int, CoinListFragment?> {
        val shouldChange = if (fragment is CoinListFragment) LAYOUT_NO_CHANGE else LAYOUT_CHANGE
        val coinListFragment = if(shouldChange == LAYOUT_CHANGE) CoinListFragment() else null
        return Pair(shouldChange, coinListFragment)
    }
    override fun popFrameTopChangeRequired(fragment: Fragment?)  : Pair<Int, Fragment?> = Pair(LAYOUT_CLEAR, null)

    override fun popFrameBottomChangeRequired(fragment: Fragment?) =  Pair(LAYOUT_CLEAR, null)

    override val constraintSet = makeConstraintSet(context)

    private fun makeConstraintSet(context: Context): ConstraintSet {
        val constraint = ConstraintSet()
        constraint.clone(context, R.layout.constraint_home)
        return constraint
    }
}
