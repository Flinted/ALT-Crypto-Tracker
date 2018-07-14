package this.chrisdid.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import this.chrisdid.alt.R
import this.chrisdid.alt.layoutCoordination.viewTransitions.viewActions.Replace
import this.chrisdid.alt.layoutCoordination.viewTransitions.viewActions.ViewAction
import this.chrisdid.alt.layoutCoordination.viewTransitions.viewActions.ViewTransition
import this.chrisdid.alt.ui.constraintui.portfoliopiechart.PortfolioPieChartFragment
import this.chrisdid.alt.ui.constraintui.trackerList.TrackerListFragment

class HomeToTrackerTransition(context: Context) : ViewStateTransition {

    private val transactions: List<ViewAction<FragmentTransaction>>
    override val constraintSet = ConstraintSet()

    init {
        val replaceCentre = Replace(R.id.frame_top, PortfolioPieChartFragment::class.java)
        val replacePopFrameBottom = Replace(R.id.pop_frame_bottom, TrackerListFragment::class.java)
        val transition = ViewTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
        constraintSet.clone(context, R.layout.constraint_tracker)
        transactions = listOf(replaceCentre, replacePopFrameBottom, transition)
    }

    override fun preExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        super.preExecute(fragmentManager, constraintLayout)
        val transaction = fragmentManager.beginTransaction()
        transactions.forEach { viewAction ->
            viewAction.execute(transaction)
        }
        transaction.commitAllowingStateLoss()
    }

    override fun postExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        return
    }
}