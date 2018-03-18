package makes.flint.alt.layoutCoordination.viewTransitions

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager

/**
 * ViewStateTransition
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface ViewStateTransition {
    val constraintSet: ConstraintSet
    fun preExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout)
    fun postExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout)
}
