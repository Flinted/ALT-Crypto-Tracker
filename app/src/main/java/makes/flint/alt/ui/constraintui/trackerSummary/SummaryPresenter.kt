package makes.flint.alt.ui.constraintui.trackerSummary

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * SummaryPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class SummaryPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<SummaryContractView>(),
        SummaryContractPresenter {

    // Properties

    private var summarySubscription: Subscription? = null

    // Lifecycle

    override fun initialise() {
        initialiseSummarySubscriber()
        view?.setFABOnClickListener()
    }

    override fun onDestroy() {
        this.summarySubscription?.unsubscribe()
        this.summarySubscription = null
    }

    // Private Functions

    private fun initialiseSummarySubscriber() {
        val subscription = dataController.summaryRefreshSubscriber()
        this.summarySubscription = subscription.first.subscribe {
            view?.updateForSummary(it)
        }
        view?.updateForSummary(subscription.second)
    }
}