package makes.flint.alt.ui.tracker.summary.summaryFragments

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * SummaryPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SummaryPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<SummaryContractView>(),
        SummaryContractPresenter {

    // Properties

    private var summarySubscription: Subscription? = null

    // Lifecycle

    override fun initialise() {
        initialiseSummarySubscriber()
    }

    override fun onDestroy() {
        this.summarySubscription?.unsubscribe()
        this.summarySubscription = null
    }

    // Private Functions

    private fun initialiseSummarySubscriber() {
        this.summarySubscription = dataController.summaryRefreshSubscriber().subscribe {
            view?.updateForSummary(it)
        }
    }
}