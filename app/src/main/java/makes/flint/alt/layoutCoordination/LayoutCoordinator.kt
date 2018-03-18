package makes.flint.alt.layoutCoordination

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.transition.AutoTransition
import android.transition.Scene
import android.transition.Transition
import android.transition.Transition.TransitionListener
import android.transition.TransitionManager
import makes.flint.alt.R
import makes.flint.alt.injection.scopes.UserScope
import javax.inject.Inject

/**
 * LayoutCoordinator
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
const val loading = "LOADING"
const val home = "HOME"
const val coin = "COIN"
const val addCoin = "ADDCOIN"
const val tracker = "TRACKER"
const val error = "ERROR"
const val search = "SEARCH"

@UserScope
class LayoutCoordinator @Inject constructor() {

    private var layouts: MutableMap<String, ConstraintSet> = makeConstraintSetsMap()
    internal var currentViewState = loading
        private set

    private fun makeConstraintSetsMap() = mutableMapOf(
            loading to ConstraintSet(),
            home to ConstraintSet(),
            coin to ConstraintSet(),
            search to ConstraintSet(),
            addCoin to ConstraintSet(),
            tracker to ConstraintSet()
    )

    internal fun initialiseConstraintsFor(layout: ConstraintLayout) {
        val context = layout.context
        layouts[loading]?.clone(layout)
        layouts[home]?.clone(context, R.layout.constraint_home)
        layouts[coin]?.clone(context, R.layout.constraint_coin)
        layouts[search]?.clone(context, R.layout.constraint_search_coins)
        layouts[tracker]?.clone(context, R.layout.constraint_tracker)
        layouts[addCoin]?.clone(context, R.layout.constraint_add_coin)
    }

    internal fun changeConstraints(viewKey: String, masterLayout: ConstraintLayout, callback: TransitionCallBack?) {
        currentViewState = viewKey
        val constraint = layouts[viewKey] ?: return
        val transition = AutoTransition()
        transition.addListener(object : TransitionListener {
            override fun onTransitionResume(p0: Transition?) {}
            override fun onTransitionPause(p0: Transition?) {}
            override fun onTransitionCancel(p0: Transition?) {}
            override fun onTransitionStart(p0: Transition?) {}
            override fun onTransitionEnd(p0: Transition?) {
                callback?.transitionCompleted()
            }
        })
        TransitionManager.go(Scene(masterLayout), transition)
        constraint.applyTo(masterLayout)
    }
}

internal interface TransitionCallBack {
    fun transitionCompleted()
}
