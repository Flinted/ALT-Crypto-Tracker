package makes.flint.alt.ui.constraintui.trackerChart

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * TrackerChartPresenter
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class TrackerChartPresenter @Inject constructor(private val dataController: DataController) :
        BasePresenter<TrackerChartContractView>(), TrackerChartContractPresenter {
    // Properties

    private var trackerItemSubscription: Subscription? = null

    // Lifecycle
    override fun initialise() {
        subscribeToCache()
    }

    fun onDestroy() {
        trackerItemSubscription?.unsubscribe()
        trackerItemSubscription = null
        detachView()
    }

    // Private Functions

    private fun subscribeToCache() {
        trackerItemSubscription = dataController.trackerRefreshSubscriber().subscribe {trackerListItems ->
            if (trackerListItems.isEmpty()) {
                view?.showNoTrackerEntriesMessage()
                return@subscribe
            }
            view?.displayTrackerEntriesChart(trackerListItems)
        }
    }
}