package makes.flint.alt.layoutCoordination.viewTransitions

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import makes.flint.alt.R
import makes.flint.alt.ui.interfaces.ListScrollController

/**
 * ViewStateTransition
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface ViewStateTransition {
    val constraintSet: ConstraintSet
    fun preExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        val fragment =  fragmentManager.findFragmentById(R.id.frame_bottom)
        (fragment as? ListScrollController)?.stopListScroll()
    }
    fun postExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout)
}
