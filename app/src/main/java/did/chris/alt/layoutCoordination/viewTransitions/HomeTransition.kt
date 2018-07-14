package did.chris.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import did.chris.alt.R
import did.chris.alt.layoutCoordination.viewTransitions.viewActions.Clear
import did.chris.alt.layoutCoordination.viewTransitions.viewActions.Replace
import did.chris.alt.layoutCoordination.viewTransitions.viewActions.ViewAction
import did.chris.alt.layoutCoordination.viewTransitions.viewActions.ViewTransition
import did.chris.alt.ui.constraintui.trackerBarChart.TrackerBarChartFragment

/**
 * HomeTransition
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class HomeTransition(context: Context) : ViewStateTransition {

    private val transactions: List<ViewAction<FragmentTransaction>>
    private val clears: List<ViewAction<ConstraintLayout>>
    override val constraintSet = ConstraintSet()

    init {
        val replaceCentre = Replace(R.id.frame_top, TrackerBarChartFragment::class.java)
        val transition = ViewTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        val clearPopBottom = Clear(R.id.pop_frame_bottom)
        val clearPopTop = Clear(R.id.pop_frame_top)
        constraintSet.clone(context, R.layout.constraint_home)
        transactions = listOf(replaceCentre, transition)
        clears = listOf(clearPopBottom, clearPopTop)
    }

    override fun preExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        super.preExecute(fragmentManager, constraintLayout)
        return
    }

    override fun postExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        val transaction = fragmentManager.beginTransaction()
        transactions.forEach { viewAction ->
            viewAction.execute(transaction)
        }
        clears.forEach { viewAction ->
            viewAction.execute(constraintLayout)
        }
        transaction.commitAllowingStateLoss()
    }
}