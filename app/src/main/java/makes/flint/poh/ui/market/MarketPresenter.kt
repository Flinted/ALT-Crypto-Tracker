package makes.flint.poh.ui.market

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

/**
 * MarketPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketPresenter @Inject constructor(private var dataController: DataController)
    : BasePresenter<MarketContractView>(), MarketContractPresenter {

    private var cacheSubscriber: Subscription? = null

    override fun initialise() {
        subscribeForCacheRefresh()
        view?.initialiseListAdapter()
        view?.initialiseSwipeRefreshListener()
        view?.initialiseScrollListener()
        view?.initialiseFABonClick()
        view?.initialiseAdapterListeners()
        refresh()
    }

    override fun refresh() {
        updateMarketSummary()
    }

    private fun subscribeForCacheRefresh() {
        cacheSubscriber = dataController.refreshSubscriber().subscribe() {
            refresh()
        }
    }

    override fun onDestroy() {
        cacheSubscriber = null
        detachView()
    }

    override fun onCoinSelected(coinSymbol: String) {
        view?.showDialogForCoin(coinSymbol)
    }

    private fun updateMarketSummary() {
        val marketData = dataController.getMarketData() ?: return
        val oneHour = marketData.oneHourAverageFormatted()
        val twentyFourHour = marketData.twentyFourHourAverageFormatted()
        val sevenDay = marketData.sevenDayAverageFormatted()
        val coins = marketData.numberItems
        view?.updateMarketSummary(oneHour, twentyFourHour, sevenDay, coins)
    }
}
