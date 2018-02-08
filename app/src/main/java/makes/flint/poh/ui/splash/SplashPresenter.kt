package makes.flint.poh.ui.splash

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * SplashPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SplashPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<SplashContractView>(),
        SplashContractPresenter {

    private var coinListSubscriber: Subscription? = null

    override fun initialise() {
        coinListSubscriber = dataController.coinRefreshSubscriber().subscribe {
            println("Refreshed SPLASH")
            view?.proceedToMainActivity()
            clearSubscription()
        }
        dataController.refreshRequested()
    }

    private fun clearSubscription() {
        this.coinListSubscriber?.unsubscribe()
        this.coinListSubscriber = null
    }
}