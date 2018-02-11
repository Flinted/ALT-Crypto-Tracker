package makes.flint.poh.ui.splash

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.configuration.POHSettings
import makes.flint.poh.configuration.SettingsData
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
        initialiseSettings()
        coinListSubscriber = dataController.coinRefreshSubscriber().subscribe {
            view?.proceedToMainActivity()
            clearSubscription()
        }
        dataController.refreshRequested()
    }

    private fun initialiseSettings() {
        val settings = dataController.getSettings() ?: SettingsData()
        POHSettings.updateSettings(settings)
        dataController.storeSettings(settings)
    }

    private fun clearSubscription() {
        this.coinListSubscriber?.unsubscribe()
        this.coinListSubscriber = null
    }
}