package makes.flint.poh.data.dataController

import makes.flint.poh.data.CMCTimeStamp
import makes.flint.poh.data.dataController.dataManagers.ApiRepository
import makes.flint.poh.data.dataController.dataManagers.RealmManager
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * DataController
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
open class DataController @Inject constructor(private val apiRepository: ApiRepository,
                                              private val realmManager: RealmManager) {

    fun getCoinList(): Observable<Array<SummaryCoinResponse>> {
        val timeStamp = realmManager.lastSyncCMCGET()
        val shouldReSync = timeStamp?.shouldReSync() ?: true
        val hasCache = apiRepository.cachedCoinList != null
        if (!shouldReSync && hasCache) {
            println("CACHED COINS")
            return apiRepository.cachedCoinList!!
        }
        println("NEW COINS")
        return apiRepository.getCoinList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun updateCMCTimeStamp() {
        val timeStamp = realmManager.lastSyncCMCGET() ?: let {
            createNewTimeStamp()
            return
        }
        updateStoredTimeStamp(timeStamp)
    }

    private fun updateStoredTimeStamp(timeStamp: CMCTimeStamp) {
        realmManager.beginTransaction()
        timeStamp.setLastSyncToNow()
        realmManager.copyOrUpdate(timeStamp)
        realmManager.commitTransaction()
    }

    private fun createNewTimeStamp() {
        val timeStamp = CMCTimeStamp()
        realmManager.beginTransaction()
        realmManager.copyOrUpdate(timeStamp)
        realmManager.commitTransaction()
    }
}
