package makes.flint.alt.layoutCoordination.viewTransitions

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentTransaction

/**
 * ViewStateTransition
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
internal interface ViewStateTransition {
    val constraintSet: ConstraintSet
    fun preExecute(transaction: FragmentTransaction, constraintLayout: ConstraintLayout)
    fun postExecute(transaction: FragmentTransaction, constraintLayout: ConstraintLayout)
}
