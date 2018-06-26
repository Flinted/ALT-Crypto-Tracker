package makes.flint.alt.layoutCoordination

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.transition.AutoTransition
import android.transition.Scene
import android.transition.Transition
import android.transition.Transition.TransitionListener
import android.transition.TransitionManager
import makes.flint.alt.injection.scopes.UserScope
import makes.flint.alt.layoutCoordination.viewTransitions.*
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
class LayoutCoordinator @Inject constructor(context: Context) {

    private var layouts: MutableMap<String, ViewStateTransition> = makeConstraintSetsMap(context)
    internal var currentViewState = loading
        private set

    private fun makeConstraintSetsMap(context: Context) = mutableMapOf(
            home to HomeTransition(context),
            search to HomeToSearchTransition(context),
            addCoin to TrackerToAddCoinTransition(context),
            tracker to HomeToTrackerTransition(context)
    )

    internal fun changeConstraints(viewKey: String,
                                   masterLayout: ConstraintLayout,
                                   fragmentManager: FragmentManager,
                                   viewStateTransition: ViewStateTransition? = null
    ) {
        if (currentViewState == viewKey) {
            return
        }
        var viewState = viewStateTransition
        if (viewState == null) {
            viewState = layouts[viewKey] ?: return
        }
        if (currentViewState == home && viewKey == home) {
            return
        }
        currentViewState = viewKey
        updateForViewStateTransition(viewState, masterLayout, fragmentManager)
    }

    private fun updateForViewStateTransition(viewStateTransition: ViewStateTransition,
                                              masterLayout: ConstraintLayout,
                                              fragmentManager: FragmentManager) {
        println("Key $viewStateTransition and ${viewStateTransition.constraintSet}")
        viewStateTransition.preExecute(fragmentManager, masterLayout)
        val transition = AutoTransition()
        transition.addListener(object : TransitionListener {
            override fun onTransitionResume(p0: Transition?) {}
            override fun onTransitionPause(p0: Transition?) {}
            override fun onTransitionCancel(p0: Transition?) {}
            override fun onTransitionStart(p0: Transition?) {}
            override fun onTransitionEnd(p0: Transition?) {
                viewStateTransition.postExecute(fragmentManager, masterLayout)
            }
        })
        TransitionManager.go(Scene(masterLayout), transition)
        viewStateTransition.constraintSet.applyTo(masterLayout)
    }
}
