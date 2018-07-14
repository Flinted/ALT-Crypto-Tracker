package this.chrisdid.alt.layoutCoordination.viewTransitions.viewActions

/**
 * ViewAction
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface ViewAction<in T> {
    fun execute(executor:T)
}