package makes.flint.alt.ui.constraintui.layoutCoordinator.layoutConfigurations

import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment

/**
 * LayoutConfiguration
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
const val LAYOUT_CHANGE = 0
const val LAYOUT_CLEAR = 1
const val LAYOUT_NO_CHANGE = 2

interface LayoutConfiguration {
    fun frameTopChangeRequired(fragment: Fragment?): Pair<Int, Fragment?>
    fun frameCentreChangeRequired(fragment: Fragment?): Pair<Int, Fragment?>
    fun frameBottomChangeRequired(fragment: Fragment?): Pair<Int, Fragment?>
    fun popFrameTopChangeRequired(fragment: Fragment?): Pair<Int, Fragment?>
    fun popFrameBottomChangeRequired(fragment: Fragment?): Pair<Int, Fragment?>
    val constraintSet: ConstraintSet
}