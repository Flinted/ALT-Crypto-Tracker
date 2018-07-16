package did.chris.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import did.chris.alt.R

class HomeToSearchTransition(context: Context) : ViewStateTransition {

    // Properties
    override val constraintSet = ConstraintSet()

    // Lifecycle
    init {
        constraintSet.clone(context, R.layout.constraint_search_coins)
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
