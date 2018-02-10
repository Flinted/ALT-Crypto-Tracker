package makes.flint.poh.ui.tracker.summary.summaryFragments

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * SummaryPresenter
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class SummaryPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<SummaryContractView>(),
        SummaryContractPresenter {
    private var summarySubscription: Subscription? = null

    override fun initialise() {
        initialiseSummarySubscriber()
    }

    private fun initialiseSummarySubscriber() {
        this.summarySubscription = dataController.summaryRefreshSubscriber().subscribe {
            view?.updateForSummary(it)
        }
    }

    override fun onDestroy() {
        this.summarySubscription?.unsubscribe()
        this.summarySubscription = null
    }
}