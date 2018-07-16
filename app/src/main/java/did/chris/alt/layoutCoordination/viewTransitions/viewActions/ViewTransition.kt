package did.chris.alt.layoutCoordination.viewTransitions.viewActions

import android.support.v4.app.FragmentTransaction



class ViewTransition(private val transitionId: Int) : ViewAction<FragmentTransaction> {

    // Overrides
    override fun execute(executor: FragmentTransaction) {
        executor.setTransition(transitionId)
    }
}
