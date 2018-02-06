package makes.flint.poh.data.dataController.dataManagers

import makes.flint.poh.configuration.POHSettings
import makes.flint.poh.data.TimeStamp
import makes.flint.poh.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.poh.data.dataController.dataSource.DataSource
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import makes.flint.poh.data.response.histoResponse.HistoricalDataResponse
import makes.flint.poh.data.services.interfaces.CMCAPIService
import makes.flint.poh.data.services.interfaces.CryptoCompareAPIService
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * APIRepository
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
@Singleton
class ApiRepository @Inject constructor(private val cmcAPIService: CMCAPIService,
                                        private val cryptoCompareAPIService: CryptoCompareAPIService
) : DataSource {

    var cachedHistoricalResponse: HashMap<String, HistoricalDataResponse> = hashMapOf()
    var lastSync: TimeStamp? = null
    var lastSyncHistorical: TimeStamp? = null

    fun coinsGET(): Observable<Array<SummaryCoinResponse>>? {
        return cmcAPIService
                .coinListGET(POHSettings.limit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getHistoricalDataFor(callback: RepositoryCallbackSingle<HistoricalDataResponse?>, coinSymbol: String) {
        val timeStamp = lastSyncHistorical ?: let {
            getHistoricalDataFromAPI(callback, coinSymbol)
            return
        }
        val cachedData = cachedHistoricalResponse[coinSymbol] ?: let {
            getHistoricalDataFromAPI(callback, coinSymbol)
            return
        }
        if (!timeStamp.shouldReSync()) {
            callback.onRetrieve(false, timeStamp.timeStampISO8601, cachedData)
        }
        getHistoricalDataFromAPI(callback, coinSymbol)
    }

    private fun getHistoricalDataFromAPI(callback: RepositoryCallbackSingle<HistoricalDataResponse?>, coinSymbol: String) {
        cryptoCompareAPIService.histoHourGET(coinSymbol, POHSettings.currency)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Subscriber<HistoricalDataResponse>() {
                    override fun onCompleted() {}
                    override fun onError(error: Throwable) = callback.onError(error)
                    override fun onNext(response: HistoricalDataResponse?) {
                        val timeStamp = TimeStamp()
                        lastSyncHistorical = timeStamp
                        callback.onRetrieve(true, timeStamp.timeStampISO8601, response)
                    }
                })
    }
}