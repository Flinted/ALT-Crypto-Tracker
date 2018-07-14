package did.chris.alt.layoutCoordination.viewTransitions.viewActions

import android.support.v4.app.FragmentTransaction
import did.chris.alt.layoutCoordination.viewTransitions.viewActions.ViewAction


/**
 * ViewTransition
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class ViewTransition(private val transitionId: Int) : ViewAction<FragmentTransaction> {

    override fun execute(executor: FragmentTransaction) {
        executor.setTransition(transitionId)
    }
}