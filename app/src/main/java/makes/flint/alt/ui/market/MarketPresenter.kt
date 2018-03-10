package makes.flint.alt.ui.market

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.utility.DateFormatter
import org.threeten.bp.ZonedDateTime
import rx.Subscription
import javax.inject.Inject

/**
 * MarketPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class MarketPresenter @Inject constructor(private var dataController: DataController)
    : BasePresenter<MarketContractView>(), MarketContractPresenter {

    // Properties

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

    override fun onDestroy() {
        lastSyncSubscriber?.unsubscribe()
        marketDataSubscriber?.unsubscribe()
        lastSyncSubscriber = null
        marketDataSubscriber = null
        detachView()
    }

    // Overrides

    override fun refresh() {
        dataController.refreshRequested()
    }

    override fun onCoinSelected(coinSymbol: String) {
        view?.showDialogForCoin(coinSymbol)
    }

    // Private Functions

    private fun updateMarketSummary(marketSummary: MarketSummaryResponse) {
        view?.updateMarketSummary(marketSummary)
    }

    private fun subscribeForCacheRefresh() {
        marketDataSubscriber = dataController.marketRefreshSubscriber().first.subscribe {
            updateMarketSummary(it)
            view?.hideLoading()
        }
        lastSyncSubscriber = dataController.lastSyncSubscriber().first.subscribe {
            val timeString = it.timeStampISO8601
            val timeStamp = ZonedDateTime.parse(timeString)
            val formattedtime = timeStamp.format(DateFormatter.TIME)
            view?.updateLastSyncTime(formattedtime)
        }
    }
}
