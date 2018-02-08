package makes.flint.poh.data.dataController

import makes.flint.poh.data.Summary
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.coinListItem.marketData.MarketData
import makes.flint.poh.data.dataController.cache.UIObjectCache
import makes.flint.poh.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.poh.data.dataController.dataManagers.ApiRepository
import makes.flint.poh.data.dataController.dataManagers.RealmManager
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import makes.flint.poh.data.response.histoResponse.HistoricalDataResponse
import makes.flint.poh.data.tracker.TrackerEntryData
import makes.flint.poh.data.trackerListItem.TrackerListItem
import rx.Observable
import rx.Subscriber
import javax.inject.Inject

/**
 * DataController
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
open class DataController @Inject constructor(private val apiRepository: ApiRepository,
                                              private val realmManager: RealmManager,
                                              private val cache: UIObjectCache
) {
    fun coinRefreshSubscriber(): Observable<List<CoinListItem>> {
        return cache.getCoinsSubscription()
    }

    fun trackerRefreshSubscriber(): Observable<List<TrackerListItem>> {
        return cache.getTrackerListSubscription()
    }

    fun summaryRefreshSubscriber(): Observable<Summary> {
        return cache.getSummarySubscription()
    }

    fun marketRefreshSubscriber(): Observable<MarketData?> {
        return cache.getMarketSubscription()
    }

    fun lastSyncSubscriber(): Observable<String> {
        return cache.getSyncTimeSubscription()
    }

    private fun updateFavouriteCoins() {
        val favouriteCoins = realmManager.getFavouriteCoins()
        cache.updateFavouriteCoins(favouriteCoins)
    }

    private fun updateTrackerEntries() {
        val trackerEntries = realmManager.getAllTrackerDataEntries()
        cache.updateTrackerEntries(trackerEntries)
    }

    private fun refreshCache() {
        val favouriteCoins = realmManager.getFavouriteCoins()
        val trackerEntryData = realmManager.getAllTrackerDataEntries()
        apiRepository.coinsGET()?.subscribe(object : Subscriber<Array<SummaryCoinResponse>>() {
            override fun onCompleted() {}
            override fun onError(error: Throwable) {
                println(error.message)
                throw error
            }

            override fun onNext(apiResponse: Array<SummaryCoinResponse>?) {
                val coinResponse = apiResponse?.toMutableList()
                cache.updateCacheForNewData(coinResponse, favouriteCoins, trackerEntryData)
                unsubscribe()
            }
        })
    }

    fun storeFavouriteCoin(favouriteCoins: FavouriteCoin) = realmManager.copyOrUpdate(favouriteCoins)

    fun getFavouriteCoin(symbol: String): FavouriteCoin? = realmManager.getFavouriteCoin(symbol)

    fun deleteFavouriteCoin(favouriteCoin: FavouriteCoin) = realmManager.remove(favouriteCoin)

    fun getCoinForSymbol(coinSymbol: String) = cache.coinListItems.find { it.symbol == coinSymbol }

    fun getHistoricalDataFor(callback: RepositoryCallbackSingle<HistoricalDataResponse?>, coinSymbol: String) {
        apiRepository.getHistoricalDataFor(callback, coinSymbol)
    }

    fun getCopyOfTrackerEntry(coinName: String?, symbol: String?): TrackerEntryData? {
        coinName ?: return null
        symbol ?: return null
        return realmManager.getCopyOfTrackerEntry(coinName, symbol)
    }

    fun storeTrackerEntry(preparedEntry: TrackerEntryData) {
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
}
