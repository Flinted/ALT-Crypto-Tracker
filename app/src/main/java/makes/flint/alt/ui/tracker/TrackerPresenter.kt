package makes.flint.alt.ui.tracker

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * TrackerPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerPresenter @Inject constructor(private var dataController: DataController) :
        BasePresenter<TrackerContractView>(),
        TrackerContractPresenter {

    private var coinListSubscriber: Subscription? = null

    override fun refreshCache() {
        dataController.refreshRequested()
    }

    override fun onDestroy() {
        detachView()
        coinListSubscriber?.unsubscribe()
        coinListSubscriber = null
    }

    override fun initialise() {
        initialiseCoinListSubscriber()
        view?.initialiseChartListeners()
        view?.initialiseAddCoinButtonListener()
        view?.initialiseConstraintSets()
        view?.initialiseTrackerList()
        view?.initialiseTrackerListListeners()
        view?.initialiseRefreshListener()
        view?.initialiseSummaryPager()
    }

    private fun initialiseCoinListSubscriber() {
        coinListSubscriber = dataController.coinRefreshSubscriber().subscribe {
            view?.hideLoading()
        }
    }

    override fun refreshTrackerEntries() {
    }
}
