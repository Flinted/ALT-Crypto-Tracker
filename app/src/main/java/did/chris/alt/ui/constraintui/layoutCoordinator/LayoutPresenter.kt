package did.chris.alt.ui.constraintui.layoutCoordinator

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * LayoutPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class LayoutPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<LayoutActivityContractView>(),
        LayoutActivityContractPresenter {

    private var timeStampSubscription: Subscription? = null
    private var errorSubscription: Subscription? = null

    override fun emitData() {
        dataController.refreshRequested()
    }

    override fun initialise() {
        view?.displayRandomLoadingMessage()
        setSubscription()
    }

    override fun onDestroy() {
        timeStampSubscription?.unsubscribe()
        errorSubscription?.unsubscribe()
        errorSubscription = null
        timeStampSubscription = null
    }

    private fun setSubscription() {
        timeStampSubscription = dataController.lastSyncSubscriber().first.subscribe {
            view?.loadInitialScreens()
            timeStampSubscription?.unsubscribe()
            timeStampSubscription = null
        }
        errorSubscription = dataController.getErrorSubscription().subscribe { error ->
            view?.displayError(error)
        }
    }
}
