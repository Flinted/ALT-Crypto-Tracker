package makes.flint.poh.data.dataController.dataManagers

import makes.flint.poh.data.dataController.dataSource.DataSource
import makes.flint.poh.data.response.APIResponseList
import makes.flint.poh.data.services.interfaces.CryptoCompareAPIService
import rx.Observable
import javax.inject.Inject

/**
 * APIManager
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class ApiManager @Inject constructor(private val cryptoCompareAPIService: CryptoCompareAPIService): DataSource {

    override fun open() {
    }

    override fun close() {
    }

    fun coinListGET(): Observable<APIResponseList> {
         return cryptoCompareAPIService.coinListGET()
    }
}