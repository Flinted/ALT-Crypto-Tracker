package makes.flint.alt.ui.constraintui.coinlist

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * CoinListPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class CoinListPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<CoinListContractView>(), CoinListContractPresenter {

    private lateinit var timeStampSubscription: Subscription

    override fun initialise() {
        subscribeForRefreshUpdates()
        view?.initialiseListAdapter()
        view?.initialiseSearchOnClick()
        view?.initialiseSwipeRefreshListener()
        view?.initialiseScrollListener()
        view?.initialiseFABonClick()
    }

    private fun subscribeForRefreshUpdates() {
        val subscription = dataController.lastSyncSubscriber()
        timeStampSubscription = subscription.first.subscribe {
            view?.hideLoading()
        }
    }

    override fun refresh() {
        dataController.refreshRequested()
    }

    override fun onCoinSelected(coinSymbol: String) {
        view?.showDialogForCoin(coinSymbol)
    }
}