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

    private var timeStampSubscription: Subscription? = null
    private var updateSubscription: Subscription? = null
    private var marketSubscription: Subscription? = null

    override fun initialise() {
        subscribeForRefreshUpdates()
        view?.initialiseSearchOnClick()
        view?.initialiseListAdapter()
        view?.initialiseSwipeRefreshListener()
        view?.initialiseScrollListener()
        view?.initialiseFABonClick()
    }

    private fun subscribeForRefreshUpdates() {
        val subscription = dataController.lastSyncSubscriber()
        timeStampSubscription = subscription.first.subscribe {
            view?.hideLoading()
        }
        updateSubscription = dataController.updatingSubscriber().subscribe {
            view?.showLoading()
        }
        val marketRefreshSubscription = dataController.marketRefreshSubscriber()
        view?.displayMarketSummary(marketRefreshSubscription.second)
        marketSubscription = marketRefreshSubscription.first.subscribe {
            view?.displayMarketSummary(it)
        }
    }

    override fun refresh() {
        dataController.refreshRequested()
    }

    override fun onCoinSelected(coinSymbol: String) {
        view?.showDialogForCoin(coinSymbol)
    }

    override fun onDestroy() {
        detachView()
        timeStampSubscription?.unsubscribe()
        updateSubscription?.unsubscribe()
        marketSubscription?.unsubscribe()
        timeStampSubscription = null
        updateSubscription = null
        marketSubscription = null
    }
}