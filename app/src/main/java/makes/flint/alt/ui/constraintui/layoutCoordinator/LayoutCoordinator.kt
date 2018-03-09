package makes.flint.alt.ui.constraintui.layoutCoordinator

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.transition.TransitionManager
import makes.flint.alt.R
import javax.inject.Inject

/**
 * LayoutCoordinator
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
const val loading = "LOADING"
const val home = "HOME"
const val coin = "COIN"
const val add = "ADD"
const val portfolio = "PORTFOLIO"
const val error = "ERROR"

class LayoutCoordinator @Inject constructor() {

    private var layouts: MutableMap<String, ConstraintSet> = makeConstraintSetsMap()

    private fun makeConstraintSetsMap() = mutableMapOf(
            loading to ConstraintSet(),
            home to ConstraintSet(),
            coin to ConstraintSet(),
            add to ConstraintSet(),
            portfolio to ConstraintSet(),
            error to ConstraintSet()
    )

    internal fun initialiseConstraintsFor(layout: ConstraintLayout) {
        val context = layout.context
        layouts[loading]?.clone(layout)
        layouts[home]?.clone(context, R.layout.constraint_home)
        layouts[coin]?.clone(context, R.layout.constraint_coin)
    }

    fun changeConstraints(viewKey: String, masterLayout: ConstraintLayout) {
        val constraint = layouts[viewKey] ?: return
        TransitionManager.beginDelayedTransition(masterLayout)
        constraint.applyTo(masterLayout)
    }

    fun updateFragments(key: String, views: LayoutViewHolder) {

    }
}
