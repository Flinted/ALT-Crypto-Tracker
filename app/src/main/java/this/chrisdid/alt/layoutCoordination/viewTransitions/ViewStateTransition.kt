package this.chrisdid.alt.layoutCoordination.viewTransitions

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import this.chrisdid.alt.R
import this.chrisdid.alt.ui.interfaces.ListScrollController

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
