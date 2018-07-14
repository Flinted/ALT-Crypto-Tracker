package makes.flint.alt.ui.constraintui.trackerSummary

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * PortfolioSummaryPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class PortfolioSummaryPresenter @Inject constructor(private val dataController: DataController) :
    BasePresenter<PortfolioContractView>(),
    PortfolioContractPresenter {

    // Properties

    private var summarySubscription: Subscription? = null
    private var updateSubscription: Subscription? = null

    // Lifecycle

    override fun initialise() {
        initialiseSummarySubscriber()
        initialiseUpdateSubscriber()
        view?.setFABOnClickListener()
    }

    override fun onDestroy() {
        this.summarySubscription?.unsubscribe()
        this.updateSubscription?.unsubscribe()
        this.updateSubscription = null
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

    private fun initialiseUpdateSubscriber() {
        this.updateSubscription = dataController.updatingSubscriber().subscribe{
            view?.showLoading()
        }
    }
}