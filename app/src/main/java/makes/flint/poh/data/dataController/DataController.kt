package makes.flint.poh.data.dataController

import makes.flint.poh.data.Summary
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.coinListItem.marketData.MarketData
import makes.flint.poh.data.dataController.cache.CacheCallback
import makes.flint.poh.data.dataController.cache.UIObjectCache
import makes.flint.poh.data.dataController.callbacks.RepositoryCallbackArray
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
    fun getCoinListNew(callback: RepositoryCallbackArray<CoinListItem>) {
        val cacheCallback = object : CacheCallback {
            override fun refreshError(error: Throwable) {
                callback.onError(error)
            }

            override fun cacheRefreshed() {
                callback.onRetrieve(true, cache.lastSyncTime(), cache.coinListItems)
            }
        }
        if (cache.coinListItemsCacheIsValid()) {
            updateFavouriteCoins(cacheCallback)
            return
        }
        refreshCache(cacheCallback)
    }

    fun refreshSubscriber(): Observable<Boolean> {
        return cache.onNewSync()
    }

    fun getTrackerListNew(callback: RepositoryCallbackArray<TrackerListItem>) {
        val cacheCallback = object : CacheCallback {
            override fun refreshError(error: Throwable) {
                callback.onError(error)
            }

            override fun cacheRefreshed() {
                callback.onRetrieve(true, cache.lastSyncTime(), cache.trackerListItems)
            }
        }
        if (cache.trackerListItemsCacheIsValid()) {
            updateTrackerEntries(cacheCallback)
            return
        }
        refreshCache(cacheCallback)
    }

    fun getSummaryNew(callback: RepositoryCallbackSingle<Summary>) {
        val cacheCallback = object : CacheCallback {
            override fun refreshError(error: Throwable) {
                callback.onError(error)
            }

            override fun cacheRefreshed() {
                callback.onRetrieve(true, cache.lastSyncTime(), cache.summary!!)
            }
        }
        if (cache.summaryCacheIsValid()) {
            callback.onRetrieve(true, cache.lastSyncTime(), cache.summary!!)
            return
        }
        refreshCache(cacheCallback)
    }

    private fun updateFavouriteCoins(cacheCallback: CacheCallback) {
        val favouriteCoins = realmManager.getFavouriteCoins()
        cache.updateFavouriteCoins(favouriteCoins, cacheCallback)
    }

    private fun updateTrackerEntries(cacheCallback: CacheCallback) {
        val trackerEntries = realmManager.getAllTrackerDataEntries()
        cache.updateTrackerEntries(trackerEntries, cacheCallback)
    }

    private fun refreshCache(cacheCallback: CacheCallback) {
        val favouriteCoins = realmManager.getFavouriteCoins()
        val trackerEntryData = realmManager.getAllTrackerDataEntries()
        apiRepository.coinsGET()?.subscribe(object : Subscriber<Array<SummaryCoinResponse>>() {
            override fun onCompleted() {}
            override fun onError(error: Throwable) {
                println("ERROR ${error.message}")
                cacheCallback.refreshError(error)
            }

            override fun onNext(apiResponse: Array<SummaryCoinResponse>?) {
                val coinResponse = apiResponse?.toMutableList()
                cache.updateCacheForNewData(coinResponse, favouriteCoins, trackerEntryData, cacheCallback)
                unsubscribe()
            }
        })
    }

    fun storeFavouriteCoin(favouriteCoins: FavouriteCoin) {
        realmManager.copyOrUpdate(favouriteCoins)
    }

    fun getFavouriteCoin(symbol: String): FavouriteCoin? {
        return realmManager.getFavouriteCoin(symbol)
    }

    fun deleteFavouriteCoin(favouriteCoin: FavouriteCoin) {
        realmManager.remove(favouriteCoin)
    }

    fun getCoinForSymbol(coinSymbol: String): CoinListItem? {
        return cache.coinListItems.find { it.symbol == coinSymbol }
    }

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
    }

    fun deleteTrackerEntryFor(id: String) {
        realmManager.deleteTrackerEntryDataFor(id)
    }

    fun deleteTransactionFor(idToDelete: String) {
        realmManager.deleteTransactionFor(idToDelete)
    }

    fun getMarketData(): MarketData? {
        return cache.marketData
    }
}
