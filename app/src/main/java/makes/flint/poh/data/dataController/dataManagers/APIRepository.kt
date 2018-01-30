package makes.flint.poh.data.dataController.dataManagers

import makes.flint.poh.data.TimeStamp
import makes.flint.poh.data.dataController.callbacks.RepositoryCallback
import makes.flint.poh.data.dataController.dataSource.DataSource
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import makes.flint.poh.data.services.interfaces.CMCAPIService
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
class ApiRepository @Inject constructor(private val cmcAPIService: CMCAPIService) : DataSource {

    var cachedCoinList: List<SummaryCoinResponse>? = null
    var lastSync: TimeStamp? = null

    fun getCoinList(callback: RepositoryCallback<SummaryCoinResponse>) {
        val timeStamp = lastSync ?: let {
            cmcAPIServiceGET(callback)
            return
        }
        if (!shouldReturnCache(timeStamp)) {
            cmcAPIServiceGET(callback)
            return
        }
        cachedCoinList?.let {
            callback.onRetrieve(false, timeStamp.timeStampISO8601, it)
            return
        }
        cmcAPIServiceGET(callback)
    }

    private fun cmcAPIServiceGET(callback: RepositoryCallback<SummaryCoinResponse>) {
        cmcAPIService
                .coinListGET(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Subscriber<Array<SummaryCoinResponse>>() {
                    override fun onCompleted() {}
                    override fun onError(error: Throwable) = callback.onError(error)
                    override fun onNext(apiResponseList: Array<SummaryCoinResponse>) {
                        val timeStamp = TimeStamp()
                        val refreshedCoinList = apiResponseList.toList()
                        lastSync = timeStamp
                        cachedCoinList = refreshedCoinList
                        callback.onRetrieve(true, timeStamp.timeStampISO8601, refreshedCoinList)
                    }
                })
    }

    private fun shouldReturnCache(timeStamp: TimeStamp): Boolean {
        val shouldReSync = timeStamp.shouldReSync()
        val hasCache = cachedCoinList != null
        return !shouldReSync && hasCache
    }
}