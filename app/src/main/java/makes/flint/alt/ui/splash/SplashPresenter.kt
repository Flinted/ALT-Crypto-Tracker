package makes.flint.alt.ui.splash

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.configuration.SettingsData
import makes.flint.alt.data.dataController.DataController
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