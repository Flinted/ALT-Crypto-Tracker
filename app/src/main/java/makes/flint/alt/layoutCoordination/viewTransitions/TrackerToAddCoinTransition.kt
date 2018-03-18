package makes.flint.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentTransaction
import makes.flint.alt.R
import makes.flint.alt.layoutCoordination.viewTransitions.viewActions.Replace
import makes.flint.alt.layoutCoordination.viewTransitions.viewActions.ViewAction
import makes.flint.alt.ui.constraintui.addCoin.AddCoinFragment

/**
 * TrackerToAddCoinTransition
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class TrackerToAddCoinTransition(context: Context): ViewStateTransition {
    private val transactions: List<ViewAction<FragmentTransaction>>
    private val clears: List<ViewAction<ConstraintLayout>>
    override val constraintSet = ConstraintSet()

    init {
        val replacePopBottom = Replace(R.id.pop_frame_bottom, AddCoinFragment::class.java)
        constraintSet.clone(context, R.layout.constraint_coin)
        transactions = listOf(replacePopBottom)
        clears = listOf()
    }
    override fun preExecute(transaction: FragmentTransaction, constraintLayout: ConstraintLayout) {
        transactions.forEach {viewAction ->
            viewAction.execute(transaction)
        }
        transaction.commit()
    }

    override fun postExecute(transaction: FragmentTransaction, constraintLayout: ConstraintLayout) {
        return
    }
}