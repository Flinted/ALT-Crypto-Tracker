package makes.flint.poh.ui.tracker.trackerList

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.Summary
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.callbacks.RepositoryCallbackArray
import makes.flint.poh.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.poh.data.trackerListItem.TrackerListItem
import rx.Subscription
import javax.inject.Inject

/**
 * TrackerAdapterPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerAdapterPresenter @Inject constructor(private var dataController: DataController
) : BasePresenter<TrackerAdapterContractView>(), TrackerAdapterContractPresenter {

    private var cacheSubscription: Subscription? = null

    override fun initialise() {
        subscribeToCache()
        refresh()
    }

    private fun refresh() {
        view?.showLoading()
        initialiseTrackerList()
        initialiseSummary()
    }

    private fun subscribeToCache() {
        cacheSubscription = dataController.refreshSubscriber().subscribe {
            refresh()
        }
    }

    fun onDestroy() {
        cacheSubscription = null
        detachView()
    }

    private fun initialiseTrackerList() {
        dataController.getTrackerListNew(object : RepositoryCallbackArray<TrackerListItem> {
            override fun onError(error: Throwable) {
            }

            override fun onRetrieve(refreshed: Boolean, lastSync: String, results: List<TrackerListItem>) {
                if (results.isEmpty()) {
                    view?.showNoTrackerEntriesMessage()
                }
                view?.trackerEntries = results.toMutableList()
                view?.didHaveEntries()
                view?.hideLoading()
            }
        })
    }

    private fun initialiseSummary() {
        dataController.getSummaryNew(object : RepositoryCallbackSingle<Summary> {
            override fun onError(error: Throwable) {
            }

            override fun onRetrieve(refreshed: Boolean, lastSync: String, result: Summary) {
                println("HI $result")
                view?.updateSummaryFragmentFor(result)
            }
        })
    }
}
