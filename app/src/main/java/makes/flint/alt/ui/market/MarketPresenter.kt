package makes.flint.alt.ui.market

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit
import rx.Subscription
import javax.inject.Inject

/**
 * MarketPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketPresenter @Inject constructor(private var dataController: DataController)
    : BasePresenter<MarketContractView>(), MarketContractPresenter {

    // Private Properties
    private var marketDataSubscriber: Subscription? = null
    private var lastSyncSubscriber: Subscription? = null

    // Lifecycle
    override fun initialise() {
        subscribeForCacheRefresh()
        view?.initialiseListAdapter()
        view?.initialiseSwipeRefreshListener()
        view?.initialiseScrollListener()
        view?.initialiseFABonClick()
        view?.initialiseAdapterListeners()
        view?.hideProgressSpinner()
    }

    override fun refresh() {
        dataController.refreshRequested()
    }

    override fun onDestroy() {
        lastSyncSubscriber?.unsubscribe()
        marketDataSubscriber?.unsubscribe()
        lastSyncSubscriber = null
        marketDataSubscriber = null
        detachView()
    }

    private fun subscribeForCacheRefresh() {
        marketDataSubscriber = dataController.marketRefreshSubscriber().subscribe {
            updateMarketSummary(it)
            view?.hideLoading()
        }
        lastSyncSubscriber = dataController.lastSyncSubscriber().subscribe {
            val timeString = it.timeStampISO8601
            val timeStamp = ZonedDateTime.parse(timeString)
            val currentTime = ZonedDateTime.now()
            val minutesPast = ChronoUnit.MINUTES.between(timeStamp, currentTime)
            view?.updateLastSyncTime(minutesPast)
        }
    }

    override fun onCoinSelected(coinSymbol: String) {
        view?.showDialogForCoin(coinSymbol)
    }

    private fun updateMarketSummary(marketSummary: MarketSummaryResponse) {
        view?.updateMarketSummary(marketSummary)
    }
}
