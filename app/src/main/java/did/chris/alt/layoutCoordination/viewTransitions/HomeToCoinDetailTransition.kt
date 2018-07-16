package did.chris.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import did.chris.alt.R
import did.chris.alt.layoutCoordination.viewTransitions.viewActions.Replace
import did.chris.alt.layoutCoordination.viewTransitions.viewActions.ViewAction
import did.chris.alt.layoutCoordination.viewTransitions.viewActions.ViewTransition
import did.chris.alt.ui.constraintui.coinDetail.coinDetailChart.CoinDetailChart
import did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary.COIN_SYMBOL_KEY
import did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary.CoinDetailSummaryFragment

class HomeToCoinDetailTransition(context: Context, coinSymbol: String) : ViewStateTransition {

    // Properties
    private val transactions: List<ViewAction<FragmentTransaction>>
    override val constraintSet = ConstraintSet()

    // Lifecycle
    init {
        val bundle = Bundle()
        bundle.putString(COIN_SYMBOL_KEY, coinSymbol)
        val replaceCentre = Replace(R.id.frame_top, CoinDetailChart::class.java, bundle)
        val replacePopFrameBottom = Replace(R.id.pop_frame_bottom, CoinDetailSummaryFragment::class.java, bundle)
        val transition = ViewTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        constraintSet.clone(context, R.layout.constraint_coin)
        transactions = listOf(replaceCentre, replacePopFrameBottom, transition)
    }

    // Overrides
    override fun preExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        super.preExecute(fragmentManager, constraintLayout)
        return
    }

    override fun postExecute(fragmentManager: FragmentManager, constraintLayout: ConstraintLayout) {
        val transaction = fragmentManager.beginTransaction()
        transactions.forEach { viewAction ->
            viewAction.execute(transaction)
        }
        transaction.commitAllowingStateLoss()
    }
}
