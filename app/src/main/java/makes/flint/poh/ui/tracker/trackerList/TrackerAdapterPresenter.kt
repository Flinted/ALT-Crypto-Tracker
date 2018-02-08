package makes.flint.poh.ui.tracker.trackerList

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * TrackerAdapterPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerAdapterPresenter @Inject constructor(private var dataController: DataController
) : BasePresenter<TrackerAdapterContractView>(), TrackerAdapterContractPresenter {

    private var trackerItemSubscription: Subscription? = null
    private var summarySubscription: Subscription? = null

    override fun initialise() {
        subscribeToCache()
    }

    private fun subscribeToCache() {
        trackerItemSubscription = dataController.trackerRefreshSubscriber().subscribe {
            if (it.isEmpty()) {
                view?.showNoTrackerEntriesMessage()
            }
            view?.trackerEntries = it.toMutableList()
            view?.didHaveEntries()
            view?.hideLoading()
        }
        summarySubscription = dataController.summaryRefreshSubscriber().subscribe{
            println("SUMMARY UPDATE")
            view?.updateSummaryFragmentFor(it)
        }
    }

    fun onDestroy() {
        trackerItemSubscription = null
        summarySubscription = null
        detachView()
    }
}
