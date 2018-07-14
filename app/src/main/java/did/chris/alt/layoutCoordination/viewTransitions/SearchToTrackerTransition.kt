package did.chris.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import did.chris.alt.R

class SearchToTrackerTransition(context: Context) : ViewStateTransition {

    override val constraintSet = ConstraintSet()

    init {
        constraintSet.clone(context, R.layout.constraint_tracker)
    }

    override fun preExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        return
    }

    override fun postExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        return
    }
}