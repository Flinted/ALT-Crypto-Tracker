package makes.flint.alt.data.dataController

import makes.flint.alt.configuration.SettingsData
import makes.flint.alt.data.Summary
import makes.flint.alt.data.TimeStamp
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.dataController.cache.UIObjectCache
import makes.flint.alt.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.alt.data.dataController.dataManagers.ApiRepository
import makes.flint.alt.data.dataController.dataManagers.RealmManager
import makes.flint.alt.data.favouriteCoins.FavouriteCoin
import makes.flint.alt.data.response.coinSummary.SummaryCoinResponse
import makes.flint.alt.data.response.histoResponse.HistoricalDataResponse
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.data.tracker.TrackerDataEntry
import makes.flint.alt.data.trackerListItem.TrackerListItem
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject
import javax.inject.Inject

/**
 * DataController
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
open class DataController @Inject constructor(private val apiRepository: ApiRepository,
                                              private val realmManager: RealmManager,
                                              private val cache: UIObjectCache
) {

    private var hasEncounteredError: PublishSubject<Throwable> = PublishSubject.create()
    internal fun getErrorSubscription() = hasEncounteredError.asObservable()

    fun coinRefreshSubscriber(): Observable<List<CoinListItem>> = cache.getCoinsSubscription()

    fun trackerRefreshSubscriber(): Observable<List<TrackerListItem>> = cache.getTrackerListSubscription()

    fun summaryRefreshSubscriber(): Observable<Summary> = cache.getSummarySubscription()

    fun marketRefreshSubscriber(): Observable<MarketSummaryResponse> = cache.getMarketSubscription()

    fun lastSyncSubscriber(): Observable<TimeStamp> = cache.getSyncTimeSubscription()

    private fun updateFavouriteCoins() {
        val favouriteCoins = realmManager.getFavouriteCoins()
        cache.updateFavouriteCoins(favouriteCoins)
    }

    private fun updateTrackerEntries() {
        val trackerEntries = realmManager.getAllTrackerDataEntries()
        cache.updateTrackerEntries(trackerEntries)
    }

    private fun refreshCache() {
        apiRepository.marketSummaryGET()?.subscribe(object : Subscriber<MarketSummaryResponse>() {
            override fun onNext(response: MarketSummaryResponse) {
                cache.updateMarketSummary(response)
                getCoinsList()
            }

            override fun onCompleted() {}
            override fun onError(error: Throwable?) {
                println(error)
                hasEncounteredError.onNext(error)
            }
        })

    }

    private fun getCoinsList() {
        val favouriteCoins = realmManager.getFavouriteCoins()
        val trackerEntryData = realmManager.getAllTrackerDataEntries()
        apiRepository.coinsGET()?.doOnNext { data ->
            cache.updateForNewData(
                    data?.toMutableList(),
                    favouriteCoins,
                    trackerEntryData)
        }?.observeOn(AndroidSchedulers.mainThread())?.subscribe(object :
                Subscriber<Array<SummaryCoinResponse>>() {
            override fun onCompleted() {}
            override fun onError(error: Throwable) {
                println(error)
                hasEncounteredError.onNext(error)
            }

            override fun onNext(apiResponse: Array<SummaryCoinResponse>?) {
                cache.emitData()
                unsubscribe()
            }
        })
    }

    fun storeFavouriteCoin(favouriteCoins: FavouriteCoin) = realmManager.copyOrUpdate(favouriteCoins)

    fun getFavouriteCoin(symbol: String): FavouriteCoin? = realmManager.getFavouriteCoin(symbol)

    fun deleteFavouriteCoin(favouriteCoin: FavouriteCoin) = realmManager.remove(favouriteCoin)

    fun getCoinForSymbol(coinSymbol: String) = cache.coinListItems.find { it.symbol == coinSymbol }

    fun getHistoricalDataFor(callback: RepositoryCallbackSingle<HistoricalDataResponse?>,
                             coinSymbol: String,
                             dataResolution: Int,
                             chartResolution: Int) {
        apiRepository.getHistoricalDataFor(callback, coinSymbol, dataResolution, chartResolution)
    }

    fun getCopyOfTrackerEntry(coinName: String?, symbol: String?): TrackerDataEntry? {
        coinName ?: return null
        symbol ?: return null
        return realmManager.getCopyOfTrackerEntry(coinName, symbol)
    }

    fun storeTrackerEntry(preparedEntry: TrackerDataEntry) {
        realmManager.copyOrUpdate(preparedEntry)
        updateTrackerEntries()
    }

    fun deleteTrackerEntryFor(id: String) {
        realmManager.deleteTrackerEntryDataFor(id)
        updateTrackerEntries()
    }

    fun deleteTransactionFor(idToDelete: String) {
        realmManager.deleteTransactionFor(idToDelete)
        updateTrackerEntries()
    }

    fun refreshRequested() {
        if (!cache.shouldReSyncData()) {
            updateFavouriteCoins()
            updateTrackerEntries()
            return
        }
        refreshCache()
    }

    fun getSettings(): SettingsData? {
        return realmManager.getSettings()
    }

    fun storeSettings(settings: SettingsData) {
        realmManager.copyOrUpdate(settings)
    }

    fun emitLastSyncTime() {
        cache.emitLastSyncTime()
    }
}
