package makes.flint.alt.ui.constraintui.layoutCoordinator.layoutConfigurations

import android.content.Context
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import makes.flint.alt.R
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart.CoinDetailChart
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary.CoinDetailSummary
import makes.flint.alt.ui.constraintui.coinlist.CoinListFragment
import makes.flint.alt.ui.constraintui.trackerSummary.SummaryFragment

/**
 * CoinConfiguration
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class CoinConfiguration(context: Context) : LayoutConfiguration {

    override fun frameTopChangeRequired(fragment: Fragment?): Pair<Int, SummaryFragment?> {
        return Pair(LAYOUT_NO_CHANGE, null)
    }
    override fun frameCentreChangeRequired(fragment: Fragment?) : Pair<Int, CoinDetailChart?> {
        return Pair(LAYOUT_CHANGE, CoinDetailChart())
    }

    override fun frameBottomChangeRequired(fragment: Fragment?) : Pair<Int, CoinListFragment?> {
        return Pair(LAYOUT_NO_CHANGE, null)
    }
    override fun popFrameTopChangeRequired(fragment: Fragment?)  : Pair<Int, Fragment?> = Pair(LAYOUT_NO_CHANGE, null)

    override fun popFrameBottomChangeRequired(fragment: Fragment?) =  Pair(LAYOUT_CHANGE, CoinDetailSummary())

    override val constraintSet = makeConstraintSet(context)

    private fun makeConstraintSet(context: Context): ConstraintSet {
        val constraint = ConstraintSet()
        constraint.clone(context, R.layout.constraint_coin)
        return constraint
    }
}
