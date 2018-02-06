package makes.flint.poh.data.dataController.cache

import makes.flint.poh.data.Summary
import makes.flint.poh.data.TimeStamp
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.coinListItem.marketData.MarketData
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import makes.flint.poh.data.response.CoinResponse
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import makes.flint.poh.data.tracker.TrackerEntryData
import makes.flint.poh.data.trackerListItem.TrackerListItem
import makes.flint.poh.factories.CoinListItemFactory
import makes.flint.poh.factories.SummaryFactory
import makes.flint.poh.factories.TrackerItemFactory
import rx.subjects.PublishSubject
import java.math.BigDecimal
import javax.inject.Inject

/**
 * UIObjectCache
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class UIObjectCache @Inject constructor(private val coinListItemFactory: CoinListItemFactory,
                                        private val trackerItemFactory: TrackerItemFactory,
                                        private val summaryFactory: SummaryFactory) {
    private var lastUpdate: TimeStamp? = null
    internal var coinListItems: List<CoinListItem> = mutableListOf()
    internal var trackerListItems: List<TrackerListItem> = mutableListOf()
    internal var summary: Summary? = Summary(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, mutableListOf())
    internal var marketData: MarketData? = null

    private var hasRefreshed: PublishSubject<Boolean> = PublishSubject.create()
    internal fun onNewSync() = hasRefreshed.asObservable()

    private fun shouldReSyncData() = lastUpdate?.shouldReSync() ?: true
    fun coinListItemsCacheIsValid() = coinListItems.isNotEmpty() && !shouldReSyncData()
    fun trackerListItemsCacheIsValid() = trackerListItems.isNotEmpty() && !shouldReSyncData()
    fun summaryCacheIsValid() = summary != null && !shouldReSyncData()
    fun lastSyncTime() = lastUpdate?.timeStampISO8601 ?: ""

    fun updateCacheForNewData(data: MutableList<SummaryCoinResponse>?, favouritesData: MutableList<FavouriteCoin>,
                              trackerData: List<TrackerEntryData>,
                              cacheCallback: CacheCallback) {
        data ?: return
        val coinResponses = data as MutableList<CoinResponse>
        val coinListItems = coinListItemFactory.makeCoinListItems(coinResponses, favouritesData)
        val trackerListItems = trackerItemFactory.makeTrackerItems(trackerData, coinListItems)
        val summary = summaryFactory.makeSummaryFor(trackerListItems)
        val marketData = coinListItemFactory.getMarketData()
        this.marketData = marketData
        this.coinListItems = coinListItems
        this.trackerListItems = trackerListItems
        this.summary = summary
        this.lastUpdate = TimeStamp()
        cacheCallback.cacheRefreshed()
        hasRefreshed.onNext(true)
    }

    fun updateFavouriteCoins(favouriteCoins: MutableList<FavouriteCoin>, cacheCallback: CacheCallback) {
        val updatedCoins = coinListItemFactory.updateFavouriteCoins(coinListItems, favouriteCoins)
        this.coinListItems = updatedCoins as List<CoinListItem>
        cacheCallback.cacheRefreshed()
    }

    fun updateTrackerEntries(trackerEntries: List<TrackerEntryData>, cacheCallback: CacheCallback) {
        val updatedTrackerEntries = trackerItemFactory.makeTrackerItems(trackerEntries, coinListItems)
        val updatedSummary = summaryFactory.makeSummaryFor(updatedTrackerEntries)
        this.trackerListItems = updatedTrackerEntries
        this.summary = updatedSummary
        cacheCallback.cacheRefreshed()
    }
}

interface CacheCallback {
    fun refreshError(error: Throwable)
    fun cacheRefreshed()
}