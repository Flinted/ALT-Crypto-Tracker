package makes.flint.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentTransaction
import makes.flint.alt.R

/**
 * HomeToSearchTransition
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class HomeToSearchTransition(context: Context) : ViewStateTransition {

    override val constraintSet = ConstraintSet()

    init {
        constraintSet.clone(context, R.layout.constraint_search_coins)
    }

    override fun preExecute(transaction: FragmentTransaction, constraintLayout: ConstraintLayout) {
        return
    }
    override fun postExecute(transaction: FragmentTransaction, constraintLayout: ConstraintLayout) {
        return
    }
}