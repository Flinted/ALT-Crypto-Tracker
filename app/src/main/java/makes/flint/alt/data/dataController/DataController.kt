package makes.flint.alt.data.dataController

import makes.flint.alt.configuration.SettingsData
import makes.flint.alt.data.dataController.cache.UIObjectCache
import makes.flint.alt.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.alt.data.dataController.dataManagers.ApiRepository
import makes.flint.alt.data.dataController.dataManagers.RealmManager
import makes.flint.alt.data.favouriteCoins.FavouriteCoin
import makes.flint.alt.data.response.coinSummary.SummaryCoinResponse
import makes.flint.alt.data.response.histoResponse.HistoricalDataResponse
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.data.tracker.TrackerDataEntry
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject
import javax.inject.Inject

/**
 * DataController
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
open class DataController @Inject constructor(private val apiRepository: ApiRepository,
                                              private val realmManager: RealmManager,
                                              private val cache: UIObjectCache
) {

    // RX Actions

    private var hasEncounteredError: PublishSubject<Throwable> = PublishSubject.create()
    internal fun getErrorSubscription() = hasEncounteredError.asObservable()

    // Internal Functions

    internal fun coinRefreshSubscriber() = cache.getCoinsSubscription()

    internal fun trackerRefreshSubscriber() = cache.getTrackerListSubscription()

    internal fun summaryRefreshSubscriber()= cache.getSummarySubscription()

    internal fun marketRefreshSubscriber() = cache.getMarketSubscription()

    internal fun lastSyncSubscriber() = cache.getSyncTimeSubscription()

    internal fun storeFavouriteCoin(favouriteCoins: FavouriteCoin) = realmManager.copyOrUpdate(favouriteCoins)

    internal fun getFavouriteCoin(symbol: String): FavouriteCoin? = realmManager.getFavouriteCoin(symbol)

    internal fun deleteFavouriteCoin(favouriteCoin: FavouriteCoin) = realmManager.remove(favouriteCoin)

    internal fun getCoinForSymbol(coinSymbol: String) = cache.coinListItems.find { it.symbol == coinSymbol }

    internal fun getSettings() = realmManager.getSettings()

    internal fun storeSettings(settings: SettingsData) = realmManager.copyOrUpdate(settings)

    internal fun emitLastSyncTime() = cache.emitLastSyncTime()

    internal fun getAllTrackerEntries() = cache.trackerListItems

    internal fun getHistoricalDataFor(callback: RepositoryCallbackSingle<HistoricalDataResponse?>,
                                      coinSymbol: String,
                                      dataResolution: Int,
                                      chartResolution: Int) {
        apiRepository.getHistoricalDataFor(callback, coinSymbol, dataResolution, chartResolution)
    }

    internal fun getCopyOfTrackerEntry(coinName: String?, symbol: String?): TrackerDataEntry? {
        coinName ?: return null
        symbol ?: return null
        return realmManager.getCopyOfTrackerEntry(coinName, symbol)
    }

    internal fun storeTrackerEntry(preparedEntry: TrackerDataEntry) {
        realmManager.copyOrUpdate(preparedEntry)
        updateTrackerEntries()
    }

    internal fun deleteTrackerEntryFor(id: String) {
        realmManager.deleteTrackerEntryDataFor(id)
        updateTrackerEntries()
    }

    internal fun deleteTransactionFor(idToDelete: String) {
        realmManager.deleteTransactionFor(idToDelete)
        updateTrackerEntries()
    }

    internal fun refreshRequested(): Boolean {
        if (!cache.shouldReSyncData()) {
            updateFavouriteCoins()
            updateTrackerEntries()
            return false
        }
        refreshCache()
        return true
    }

    // Private Functions

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
        }?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Subscriber<Array<SummaryCoinResponse>>() {
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
}
