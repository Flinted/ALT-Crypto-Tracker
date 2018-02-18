package makes.flint.alt.ui.tracker.trackerList

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
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
                println("NO TRACKER ENTRIES")
                view?.showNoTrackerEntriesMessage()
                return@subscribe
            }
            view?.trackerEntries = it.toMutableList()
            view?.didHaveEntries()
            view?.hideLoading()
        }
    }

    fun onDestroy() {
        trackerItemSubscription = null
        summarySubscription = null
        detachView()
    }
}
