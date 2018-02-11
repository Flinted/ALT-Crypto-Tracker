package makes.flint.poh.data.dataController.cache

import makes.flint.poh.data.Summary
import makes.flint.poh.data.TimeStamp
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.coinListItem.marketData.MarketData
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import makes.flint.poh.data.response.CoinResponse
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import makes.flint.poh.data.tracker.TrackerDataEntry
import makes.flint.poh.data.trackerListItem.TrackerListItem
import makes.flint.poh.factories.CoinListItemFactory
import makes.flint.poh.factories.SummaryFactory
import makes.flint.poh.factories.TrackerItemFactory
import rx.subjects.PublishSubject
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
    internal var summary: Summary = summaryFactory.makeEmptySummary()
    internal var marketData: MarketData? = null

    private var hasRefreshedCoins: PublishSubject<List<CoinListItem>> = PublishSubject.create()
    private var hasRefreshedTrackerItems: PublishSubject<List<TrackerListItem>> = PublishSubject.create()
    private var hasRefreshedSummary: PublishSubject<Summary> = PublishSubject.create()
    private var hasRefreshedMarketData: PublishSubject<MarketData> = PublishSubject.create()
    private var hasUpdatedTimeStamp: PublishSubject<TimeStamp> = PublishSubject.create()
    internal fun getCoinsSubscription() = hasRefreshedCoins.asObservable()
    internal fun getTrackerListSubscription() = hasRefreshedTrackerItems.asObservable()
    internal fun getSummarySubscription() = hasRefreshedSummary.asObservable()
    internal fun getMarketSubscription() = hasRefreshedMarketData.asObservable()
    internal fun getSyncTimeSubscription() = hasUpdatedTimeStamp.asObservable()

    fun shouldReSyncData() = lastUpdate?.shouldReSync() ?: true

    fun updateCacheForNewData(data: MutableList<SummaryCoinResponse>?, favouritesData: MutableList<FavouriteCoin>,
                              trackerData: List<TrackerDataEntry>) {
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
        hasRefreshedCoins.onNext(coinListItems)
        hasRefreshedTrackerItems.onNext(trackerListItems)
        hasRefreshedSummary.onNext(summary)
        hasRefreshedMarketData.onNext(marketData)
        hasUpdatedTimeStamp.onNext(lastUpdate)
    }

    fun updateFavouriteCoins(favouriteCoins: MutableList<FavouriteCoin>) {
        val updatedCoins = coinListItemFactory.updateFavouriteCoins(coinListItems, favouriteCoins)
        this.coinListItems = updatedCoins as List<CoinListItem>
        hasRefreshedCoins.onNext(updatedCoins)
        hasRefreshedMarketData.onNext(marketData)
        hasUpdatedTimeStamp.onNext(lastUpdate)
    }

    fun updateTrackerEntries(trackerEntries: List<TrackerDataEntry>) {
        val updatedTrackerEntries = trackerItemFactory.makeTrackerItems(trackerEntries, coinListItems)
        val updatedSummary = summaryFactory.makeSummaryFor(updatedTrackerEntries)
        this.trackerListItems = updatedTrackerEntries
        this.summary = updatedSummary
        hasRefreshedTrackerItems.onNext(trackerListItems)
        hasRefreshedSummary.onNext(summary)
    }
}
