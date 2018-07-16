package did.chris.alt.ui.constraintui.layoutCoordinator

import did.chris.alt.layoutCoordination.viewTransitions.ViewStateTransition

internal interface LayoutCoordinatable {

    // Functions
    fun updateLayout(key: String, viewStateTransition: ViewStateTransition? = null)
}