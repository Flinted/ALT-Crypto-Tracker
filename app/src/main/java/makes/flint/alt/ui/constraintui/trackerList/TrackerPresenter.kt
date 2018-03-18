package makes.flint.alt.ui.constraintui.trackerList

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * TrackerPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerPresenter @Inject constructor(private var dataController: DataController) :
        BasePresenter<TrackerContractView>(),
        TrackerContractPresenter {

    // Properties

    private var coinListSubscriber: Subscription? = null
    private var updateSubscription: Subscription? = null

    // Lifecycle

    override fun initialise() {
        initialiseSubscriptions()
        view?.initialiseAddCoinButtonListener()
        view?.initialiseTrackerList()
        view?.initialiseTrackerListListeners()
        view?.initialiseRefreshListener()
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
    }
}
