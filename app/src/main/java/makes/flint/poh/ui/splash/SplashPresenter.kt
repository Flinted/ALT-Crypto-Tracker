package makes.flint.poh.ui.splash

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.callbacks.RepositoryCallbackArray
import makes.flint.poh.errors.ErrorHandler
import javax.inject.Inject

/**
 * SplashPresenter
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class SplashPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<SplashContractView>(),
        SplashContractPresenter {

    override fun initialise() {
        dataController.getCoinListNew(object : RepositoryCallbackArray<CoinListItem> {
            override fun onError(error: Throwable) {
                view?.showError(ErrorHandler.API_FAILURE)
            }

            override fun onRetrieve(refreshed: Boolean, lastSync: String, results: List<CoinListItem>) {
                view?.proceedToMainActivity()
            }
        })
    }
}