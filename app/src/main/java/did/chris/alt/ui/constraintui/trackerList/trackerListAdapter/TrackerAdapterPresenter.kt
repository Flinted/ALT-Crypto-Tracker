package did.chris.alt.ui.constraintui.trackerList.trackerListAdapter

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
import did.chris.alt.data.trackerListItem.TrackerListItem
import rx.Subscription
import javax.inject.Inject


class TrackerAdapterPresenter @Inject constructor(
    private var dataController: DataController
) : BasePresenter<TrackerAdapterContractView>(), TrackerAdapterContractPresenter {

    // Properties
    private var trackerItemSubscription: Subscription? = null
    private var summarySubscription: Subscription? = null

    // Lifecycle
    override fun initialise() {
        subscribeToCache()
    }

    internal fun onDestroy() {
        trackerItemSubscription?.unsubscribe()
        summarySubscription?.unsubscribe()
        trackerItemSubscription = null
        summarySubscription = null
        detachView()
    }

    // Private Functions
    private fun subscribeToCache() {
        val subscription = dataController.trackerRefreshSubscriber()
        trackerItemSubscription = subscription.first.subscribe {
            onGetTrackerListSuccess(it)
        }
        onGetTrackerListSuccess(subscription.second)
    }

    private fun onGetTrackerListSuccess(trackerListItems: List<TrackerListItem>) {
        if (trackerListItems.isEmpty()) {
            view?.showNoTrackerEntriesMessage()
            return
        }
        view?.trackerEntries = trackerListItems.toMutableList()
        view?.didHaveEntries()
        view?.hideLoading()
    }
}
