package makes.flint.alt.data.dataController.dataManagers

import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.dataController.callbacks.RepositoryCallbackSingle
import makes.flint.alt.data.dataController.dataSource.DataSource
import makes.flint.alt.data.response.coinSummary.SummaryCoinResponse
import makes.flint.alt.data.response.histoResponse.HistoricalDataResponse
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.data.services.interfaces.CMCAPIService
import makes.flint.alt.data.services.interfaces.CryptoCompareAPIService
import makes.flint.alt.ui.market.coinDetail.HOUR_DATA
import makes.flint.alt.ui.market.coinDetail.MINUTE_DATA
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

    fun coinsGET(): Observable<Array<SummaryCoinResponse>>? {
        return cmcAPIService
                .coinListGET(POHSettings.limit)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
    }

    fun marketSummaryGET(): Observable<MarketSummaryResponse>? {
        return cmcAPIService
                .marketSummaryGET()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getHistoricalDataFor(callback: RepositoryCallbackSingle<HistoricalDataResponse?>, coinSymbol: String, dataResolution: Int, chartResolution: Int) {
        when (dataResolution) {
            MINUTE_DATA -> getMinuteHistoricalData(callback, coinSymbol, dataResolution, chartResolution)
            HOUR_DATA -> getHourHistoricalData(callback, coinSymbol, dataResolution, chartResolution)
            else -> getDayHistoricalData(callback, coinSymbol, dataResolution, chartResolution)
        }
    }

    private fun getMinuteHistoricalData(callback: RepositoryCallbackSingle<HistoricalDataResponse?>,
                                        coinSymbol: String,
                                        dataResolution: Int,
                                        chartResolution: Int) {
        cryptoCompareAPIService.histoMinuteGET(coinSymbol, POHSettings.currency, "CCCAGG")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(makeSubscriber(callback, dataResolution, chartResolution))
    }

    private fun getHourHistoricalData(callback: RepositoryCallbackSingle<HistoricalDataResponse?>,
                                      coinSymbol: String,
                                      dataResolution: Int,
                                      chartResolution: Int) {
        cryptoCompareAPIService.histoHourGET(coinSymbol, POHSettings.currency, "CCCAGG")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(makeSubscriber(callback, dataResolution, chartResolution))
    }

    private fun getDayHistoricalData(callback: RepositoryCallbackSingle<HistoricalDataResponse?>, coinSymbol:
    String, dataResolution: Int, chartResolution: Int) {
        cryptoCompareAPIService.histoDayGET(coinSymbol, POHSettings.currency, "CCCAGG", 365)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(makeSubscriber(callback, dataResolution, chartResolution))
    }

    private fun makeSubscriber(callback: RepositoryCallbackSingle<HistoricalDataResponse?>,
                               dataResolution: Int,
                               chartResolution: Int): Subscriber<HistoricalDataResponse> {
        return object : Subscriber<HistoricalDataResponse>() {
            override fun onCompleted() {}
            override fun onError(error: Throwable) = callback.onError(error)
            override fun onNext(response: HistoricalDataResponse?) {
                callback.onRetrieve(true, dataResolution, chartResolution, response)
                unsubscribe()
            }
        }
    }
}
