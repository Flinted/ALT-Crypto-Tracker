package makes.flint.alt.ui.constraintui.trackerList

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * TrackerListPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerListPresenter @Inject constructor(private var dataController: DataController) :
        BasePresenter<TrackerContractView>(),
        TrackerContractPresenter {

    // Properties

    private var coinListSubscriber: Subscription? = null
    private var updateSubscription: Subscription? = null
    private var marketSummarySubscription: Subscription? = null

    // Lifecycle

    override fun initialise() {
        initialiseSubscriptions()
        view?.initialiseTrackerList()
        view?.initialiseTrackerListListeners()
        view?.initialiseRefreshListener()
        view?.initialiseSearchBar()
    }

    override fun onDestroy() {
        detachView()
        coinListSubscriber?.unsubscribe()
        updateSubscription?.unsubscribe()
        coinListSubscriber = null
        updateSubscription = null
    }

    // Overrides

    override fun refreshCache() {
        dataController.refreshRequested()
    }

    override fun refreshTrackerEntries() {
    }

    // Private Functions

    private fun initialiseSubscriptions() {
        coinListSubscriber = dataController.coinRefreshSubscriber().first.subscribe {
            view?.hideLoading()
        }
        updateSubscription = dataController.updatingSubscriber().subscribe {
            view?.showLoading()
        }
        val subscription = dataController.marketRefreshSubscriber()
        marketSummarySubscription  = subscription.first.subscribe {
            view?.displayMarketSummary(it)
        }
        view?.displayMarketSummary(subscription.second)
    }
}
