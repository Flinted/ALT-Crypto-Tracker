package did.chris.alt.layoutCoordination.viewTransitions

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import did.chris.alt.R
import did.chris.alt.ui.interfaces.ListScrollController


interface ViewStateTransition {

    // Properties
    val constraintSet: ConstraintSet

    // Functions
    fun preExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        val fragment =  fragmentManager.findFragmentById(R.id.frame_bottom)
        (fragment as? ListScrollController)?.stopListScroll()
    }
    fun postExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout)
}
