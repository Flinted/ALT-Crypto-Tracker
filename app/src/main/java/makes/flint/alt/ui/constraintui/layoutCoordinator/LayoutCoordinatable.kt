package makes.flint.alt.ui.constraintui.layoutCoordinator

import makes.flint.alt.layoutCoordination.viewTransitions.ViewStateTransition

/**
 * LayoutCoordinatable
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
internal interface LayoutCoordinatable {

    fun updateLayout(key: String, viewStateTransition: ViewStateTransition? = null)
}