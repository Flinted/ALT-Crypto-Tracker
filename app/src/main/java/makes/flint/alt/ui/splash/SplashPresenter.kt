package makes.flint.alt.ui.splash

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.configuration.SettingsData
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.errors.ErrorHandler
import rx.Subscription
import javax.inject.Inject

/**
 * SplashPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SplashPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<SplashContractView>(),
        SplashContractPresenter {

    private var coinListSubscriber: Subscription? = null
    private var errorSubscriber: Subscription? = null

    override fun initialise() {
        initialiseSettings()
        this.coinListSubscriber = dataController.coinRefreshSubscriber().subscribe {
            proceedToApp()
            clearSubscription()
        }
        this.errorSubscriber = dataController.getErrorSubscription().subscribe {
            view?.showError(ErrorHandler.GENERAL_ERROR)
        }
        dataController.refreshRequested()
    }

    private fun proceedToApp() {
        if(!POHSettings.firstLoad) {
            view?.proceedToMainActivity()
            return
        }
        view?.proceedToOnboardActivity()
        dataController.updateAsFirstLoadComplete()
    }

    private fun initialiseSettings() {
        val settings = dataController.getSettings() ?: SettingsData()
        POHSettings.updateSettings(settings)
        dataController.storeSettings(settings)
    }

    private fun clearSubscription() {
        this.coinListSubscriber?.unsubscribe()
        this.errorSubscriber?.unsubscribe()
        this.coinListSubscriber = null
        this.errorSubscriber = null
    }
}