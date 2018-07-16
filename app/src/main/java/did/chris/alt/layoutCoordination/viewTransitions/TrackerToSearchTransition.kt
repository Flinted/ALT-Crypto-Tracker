package did.chris.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import did.chris.alt.R

class TrackerToSearchTransition(context: Context) : ViewStateTransition {

    // Properties
    override val constraintSet = ConstraintSet()

    // Lifecycle
    init {
        constraintSet.clone(context, R.layout.constraint_search_tracker)
    }

    // Overrides
    override fun preExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        super.preExecute(fragmentManager, constraintLayout)
        return
    }

    override fun postExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        return
    }
}
