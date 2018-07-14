package makes.flint.alt.layoutCoordination.viewTransitions

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import makes.flint.alt.R
import makes.flint.alt.layoutCoordination.viewTransitions.viewActions.Replace
import makes.flint.alt.layoutCoordination.viewTransitions.viewActions.ViewAction
import makes.flint.alt.layoutCoordination.viewTransitions.viewActions.ViewTransition
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart.CoinDetailChart
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary.COIN_SYMBOL_KEY
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary.CoinDetailSummary

class HomeToCoinDetailTransition(context: Context, coinSymbol: String) : ViewStateTransition {

    private val transactions: List<ViewAction<FragmentTransaction>>
    override val constraintSet = ConstraintSet()

    init {
        val bundle = Bundle()
        bundle.putString(COIN_SYMBOL_KEY, coinSymbol)
        val replaceCentre = Replace(R.id.frame_top, CoinDetailChart::class.java, bundle)
        val replacePopFrameBottom = Replace(R.id.pop_frame_bottom, CoinDetailSummary::class.java, bundle)
        val transition = ViewTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        constraintSet.clone(context, R.layout.constraint_coin)
        transactions = listOf(replaceCentre, replacePopFrameBottom, transition)
    }

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
