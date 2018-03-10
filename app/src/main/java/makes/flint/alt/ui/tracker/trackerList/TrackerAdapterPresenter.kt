package makes.flint.alt.ui.tracker.trackerList

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * TrackerAdapterPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerAdapterPresenter @Inject constructor(private var dataController: DataController
) : BasePresenter<TrackerAdapterContractView>(), TrackerAdapterContractPresenter {

    // Properties

    private var trackerItemSubscription: Subscription? = null
    private var summarySubscription: Subscription? = null

    // Lifecycle
    override fun initialise() {
        subscribeToCache()
    }

    fun onDestroy() {
        trackerItemSubscription = null
        summarySubscription = null
        detachView()
    }

    // Private Functions

    private fun subscribeToCache() {
        trackerItemSubscription = dataController.trackerRefreshSubscriber().first.subscribe {
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
}
