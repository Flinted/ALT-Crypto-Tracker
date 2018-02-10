package makes.flint.poh.ui.market

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.coinListItem.marketData.MarketData
import makes.flint.poh.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * MarketPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketPresenter @Inject constructor(private var dataController: DataController)
    : BasePresenter<MarketContractView>(), MarketContractPresenter {

    private var marketDataSubscriber: Subscription? = null
    private var lastSyncSubscriber: Subscription? = null

    override fun initialise() {
        subscribeForCacheRefresh()
        view?.initialiseListAdapter()
        view?.initialiseSwipeRefreshListener()
        view?.initialiseScrollListener()
        view?.initialiseFABonClick()
        view?.initialiseAdapterListeners()
    }

    override fun refresh() {
        dataController.refreshRequested()
    }

    private fun subscribeForCacheRefresh() {
        marketDataSubscriber = dataController.marketRefreshSubscriber().subscribe {
            updateMarketSummary(it)
            view?.hideLoading()
        }
        lastSyncSubscriber = dataController.lastSyncSubscriber().subscribe{
            view?.updateLastSyncTime(it)
        }
    }

    override fun onDestroy() {
        lastSyncSubscriber?.unsubscribe()
        marketDataSubscriber?.unsubscribe()
        lastSyncSubscriber = null
        marketDataSubscriber = null
        detachView()
    }

    override fun onCoinSelected(coinSymbol: String) {
        view?.showDialogForCoin(coinSymbol)
    }

    private fun updateMarketSummary(marketData: MarketData?) {
        marketData ?: return
        val oneHour = marketData.oneHourAverageFormatted()
        val twentyFourHour = marketData.twentyFourHourAverageFormatted()
        val sevenDay = marketData.sevenDayAverageFormatted()
        val coins = marketData.numberItems
        view?.updateMarketSummary(oneHour, twentyFourHour, sevenDay, coins)
    }
}
