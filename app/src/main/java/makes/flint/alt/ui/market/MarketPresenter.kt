package makes.flint.alt.ui.market

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.coinListItem.marketData.MarketData
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.utility.DateFormatter
import org.threeten.bp.ZonedDateTime
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
            val formattedTimeStamp = ZonedDateTime.parse(timeString).format(DateFormatter.DATE_TIME)
            view?.updateLastSyncTime(formattedTimeStamp)
        }
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
