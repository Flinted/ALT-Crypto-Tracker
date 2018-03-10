package makes.flint.alt.ui.tracker

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

    // Lifecycle

    override fun initialise() {
        initialiseCoinListSubscriber()
        view?.initialiseChartListeners()
        view?.initialiseAddCoinButtonListener()
        view?.initialiseShowSnapShotButtonListener()
        view?.initialiseTrackerList()
        view?.initialiseTrackerListListeners()
        view?.initialiseRefreshListener()
        view?.initialiseSummaryPager()
        view?.hideProgressSpinner()
    }

    override fun onDestroy() {
        detachView()
        coinListSubscriber?.unsubscribe()
        coinListSubscriber = null
    }

    // Overrides

    override fun refreshCache() {
        dataController.refreshRequested()
    }

    override fun refreshTrackerEntries() {
    }

    // Private Functions

    private fun initialiseCoinListSubscriber() {
        coinListSubscriber = dataController.coinRefreshSubscriber().first.subscribe {
            view?.hideLoading()
        }
    }
}
