package did.chris.alt.data.dataController.dataManagers

import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.data.dataController.callbacks.RepositoryCallbackSingle
import did.chris.alt.data.dataController.dataSource.DataSource
import did.chris.alt.data.response.coinSummary.SummaryCoinResponse
import did.chris.alt.data.response.histoResponse.HistoricalDataResponse
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse
import did.chris.alt.data.services.interfaces.CMCAPIService
import did.chris.alt.data.services.interfaces.CryptoCompareAPIService
import did.chris.alt.ui.constraintui.coinDetail.coinDetailChart.HOUR_DATA
import did.chris.alt.ui.constraintui.coinDetail.coinDetailChart.MINUTE_DATA
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val cmcAPIService: CMCAPIService,
    private val cryptoCompareAPIService: CryptoCompareAPIService
) : DataSource {

    // Internal Functions
    internal fun coinsGET(): Observable<Array<SummaryCoinResponse>>? {
        return cmcAPIService
            .coinListGET(ALTSharedPreferences.getMarketLimit())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
    }

    internal fun marketSummaryGET(): Observable<MarketSummaryResponse>? {
        return cmcAPIService
            .marketSummaryGET()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    internal fun getHistoricalDataFor(
        callback: RepositoryCallbackSingle<HistoricalDataResponse?>,
        coinSymbol: String,
        dataResolution: Int,
        chartResolution: Int
    ) {
        when (dataResolution) {
            MINUTE_DATA -> getMinuteHistoricalData(
                callback,
                coinSymbol,
                dataResolution,
                chartResolution
            )
            HOUR_DATA   -> getHourHistoricalData(
                callback,
                coinSymbol,
                dataResolution,
                chartResolution
            )
            else        -> getDayHistoricalData(
                callback,
                coinSymbol,
                dataResolution,
                chartResolution
            )
        }
    }

    // Private Functions
    private fun getMinuteHistoricalData(
        callback: RepositoryCallbackSingle<HistoricalDataResponse?>,
        coinSymbol: String,
        dataResolution: Int,
        chartResolution: Int
    ) {
        cryptoCompareAPIService.histoMinuteGET(
            coinSymbol,
            ALTSharedPreferences.getCurrencyCode(),
            ALTSharedPreferences.getExchange()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(makeSubscriber(callback, dataResolution, chartResolution))
    }

    private fun getHourHistoricalData(
        callback: RepositoryCallbackSingle<HistoricalDataResponse?>,
        coinSymbol: String,
        dataResolution: Int,
        chartResolution: Int
    ) {
        cryptoCompareAPIService.histoHourGET(
            coinSymbol,
            ALTSharedPreferences.getCurrencyCode(),
            ALTSharedPreferences.getExchange()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(makeSubscriber(callback, dataResolution, chartResolution))
    }

    private fun getDayHistoricalData(
        callback: RepositoryCallbackSingle<HistoricalDataResponse?>, coinSymbol:
        String, dataResolution: Int, chartResolution: Int
    ) {
        cryptoCompareAPIService.histoDayGET(
            coinSymbol,
            ALTSharedPreferences.getCurrencyCode(),
            ALTSharedPreferences.getExchange(),
            1500
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(makeSubscriber(callback, dataResolution, chartResolution))
    }

    private fun makeSubscriber(
        callback: RepositoryCallbackSingle<HistoricalDataResponse?>,
        dataResolution: Int,
        chartResolution: Int
    ): Subscriber<HistoricalDataResponse> {
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
