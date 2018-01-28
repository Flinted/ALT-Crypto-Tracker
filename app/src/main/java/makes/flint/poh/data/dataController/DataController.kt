package makes.flint.poh.data.dataController

import makes.flint.poh.data.dataController.dataManagers.ApiManager
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
open class DataController @Inject constructor(private val apiManager: ApiManager,
                                              private val realmManager: RealmManager) {

    fun getCoinList(): Observable<Array<SummaryCoinResponse>> {
        return apiManager.getCoinList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}
