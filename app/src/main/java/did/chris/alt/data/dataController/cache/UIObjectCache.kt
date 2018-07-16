package did.chris.alt.data.dataController.cache

import android.os.Process
import android.os.Process.setThreadPriority
import did.chris.alt.data.Summary
import did.chris.alt.data.TimeStamp
import did.chris.alt.data.coinListItem.CoinListItem
import did.chris.alt.data.favouriteCoins.FavouriteCoin
import did.chris.alt.data.response.CoinResponse
import did.chris.alt.data.response.coinSummary.SummaryCoinResponse
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse
import did.chris.alt.data.tracker.TrackerDataEntry
import did.chris.alt.data.trackerListItem.TrackerListItem
import did.chris.alt.factories.CoinListItemFactory
import did.chris.alt.factories.SummaryFactory
import did.chris.alt.factories.TrackerItemFactory
import rx.subjects.PublishSubject
import javax.inject.Inject

class UIObjectCache @Inject constructor(
    private val coinListItemFactory: CoinListItemFactory,
    private val trackerItemFactory: TrackerItemFactory,
    private val summaryFactory: SummaryFactory
) {

    // Properties
    private var lastUpdate: TimeStamp? = null
    private var marketSummary: MarketSummaryResponse? = null
    internal var coinListItems: List<CoinListItem> = mutableListOf()
    internal var trackerListItems: List<TrackerListItem> = mutableListOf()
    internal var summary: Summary = summaryFactory.makeEmptySummary()

    // RX Subscriptions
    private var hasRefreshedCoins: PublishSubject<List<CoinListItem>> = PublishSubject.create()
    private var hasRefreshedTrackerItems: PublishSubject<List<TrackerListItem>> =
        PublishSubject.create()
    private var hasRefreshedSummary: PublishSubject<Summary> = PublishSubject.create()
    private var hasRefreshedMarketData: PublishSubject<MarketSummaryResponse> =
        PublishSubject.create()
    private var hasUpdatedTimeStamp: PublishSubject<TimeStamp> = PublishSubject.create()
    private var hasStartedUpdate: PublishSubject<Boolean> = PublishSubject.create()

    internal fun getUpdateBegunSubscriber() = hasStartedUpdate.asObservable()
    internal fun getCoinsSubscription() = Pair(hasRefreshedCoins.asObservable(), coinListItems)
    internal fun getTrackerListSubscription() =
        Pair(hasRefreshedTrackerItems.asObservable(), trackerListItems)

    internal fun getSummarySubscription() = Pair(hasRefreshedSummary.asObservable(), summary)
    internal fun getMarketSubscription() =
        Pair(hasRefreshedMarketData.asObservable(), marketSummary)

    internal fun getSyncTimeSubscription() = Pair(hasUpdatedTimeStamp.asObservable(), lastUpdate)

    // Internal Functions
    internal fun shouldReSyncData() = lastUpdate?.shouldReSync() ?: true

    internal fun updateMarketSummary(marketSummary: MarketSummaryResponse) {
        this.marketSummary = marketSummary
    }

    @Suppress("UNCHECKED_CAST")
    internal fun updateForNewData(
        data: MutableList<SummaryCoinResponse>?,
        favouritesData: MutableList<FavouriteCoin>,
        trackerData: List<TrackerDataEntry>
    ) {
        data ?: return
        setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        val coinResponses = data as MutableList<CoinResponse>
        val coinListItems = coinListItemFactory.makeCoinListItems(coinResponses, favouritesData)
        val trackerListItems = trackerItemFactory.makeTrackerItems(trackerData, coinListItems)
        val summary = summaryFactory.makeSummaryFor(trackerListItems)
        val marketData = coinListItemFactory.getMarketData()
        this.marketSummary?.marketData = marketData
        this.coinListItems = coinListItems
        this.trackerListItems = trackerListItems
        this.summary = summary
        this.lastUpdate = TimeStamp()
    }

    internal fun emitData() {
        hasRefreshedCoins.onNext(coinListItems)
        hasRefreshedTrackerItems.onNext(trackerListItems)
        hasRefreshedSummary.onNext(summary)
        hasRefreshedMarketData.onNext(marketSummary)
        hasUpdatedTimeStamp.onNext(lastUpdate)
    }

    internal fun updateFavouriteCoins(favouriteCoins: MutableList<FavouriteCoin>) {
        val updatedCoins = coinListItemFactory.updateFavouriteCoins(coinListItems, favouriteCoins)
        this.coinListItems = updatedCoins as List<CoinListItem>
        hasRefreshedCoins.onNext(updatedCoins)
        hasRefreshedMarketData.onNext(marketSummary)
        hasUpdatedTimeStamp.onNext(lastUpdate)
    }

    internal fun updateTrackerEntries(trackerEntries: List<TrackerDataEntry>) {
        val updatedTrackerEntries =
            trackerItemFactory.makeTrackerItems(trackerEntries, coinListItems)
        val updatedSummary = summaryFactory.makeSummaryFor(updatedTrackerEntries)
        this.trackerListItems = updatedTrackerEntries
        this.summary = updatedSummary
        hasRefreshedTrackerItems.onNext(trackerListItems)
        hasRefreshedSummary.onNext(summary)
    }

    internal fun emitLastSyncTime() {
        lastUpdate ?: return
        hasUpdatedTimeStamp.onNext(lastUpdate)
    }

    internal fun startingUpdate() {
        hasStartedUpdate.onNext(true)
    }

    internal fun invalidateData() {
        lastUpdate?.invalidate()
    }
}
