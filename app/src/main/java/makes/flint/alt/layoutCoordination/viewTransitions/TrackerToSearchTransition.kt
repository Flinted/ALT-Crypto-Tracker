package makes.flint.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import makes.flint.alt.R

class TrackerToSearchTransition(context: Context) : ViewStateTransition {

    override val constraintSet = ConstraintSet()

    init {
        constraintSet.clone(context, R.layout.constraint_search_tracker)
    }

    override fun preExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        super.preExecute(fragmentManager, constraintLayout)
        return
    }

    override fun postExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        return
    }
}