package makes.flint.alt.data.dataController.cache

import android.os.Process
import android.os.Process.setThreadPriority
import makes.flint.alt.data.Summary
import makes.flint.alt.data.TimeStamp
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.coinListItem.marketData.MarketData
import makes.flint.alt.data.favouriteCoins.FavouriteCoin
import makes.flint.alt.data.response.CoinResponse
import makes.flint.alt.data.response.coinSummary.SummaryCoinResponse
import makes.flint.alt.data.tracker.TrackerDataEntry
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.factories.CoinListItemFactory
import makes.flint.alt.factories.SummaryFactory
import makes.flint.alt.factories.TrackerItemFactory
import rx.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

/**
 * UIObjectCache
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class UIObjectCache @Inject constructor(private val coinListItemFactory: CoinListItemFactory,
                                        private val trackerItemFactory: TrackerItemFactory,
                                        private val summaryFactory: SummaryFactory) {
    private var timer: Timer? = null
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

    @Suppress("UNCHECKED_CAST")
    fun updateCacheForNewData(data: MutableList<SummaryCoinResponse>?, favouritesData: MutableList<FavouriteCoin>,
                              trackerData: List<TrackerDataEntry>) {
        data ?: return
        val runnable = Runnable {
            setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
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
            emitData()
        }
        runnable.run()
    }

    private fun emitData() {
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

    fun emitLastSyncTime() {
        lastUpdate ?: return
        hasUpdatedTimeStamp.onNext(lastUpdate)
    }
}
