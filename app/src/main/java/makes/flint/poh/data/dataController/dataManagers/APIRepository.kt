package makes.flint.poh.data.dataController.dataManagers

import makes.flint.poh.data.dataController.dataSource.DataSource
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import makes.flint.poh.data.services.interfaces.CMCAPIService
import rx.Observable
import javax.inject.Inject

/**
 * APIRepository
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class ApiRepository @Inject constructor(private val cmcAPIService: CMCAPIService) : DataSource {

    var cachedCoinList: Observable<Array<SummaryCoinResponse>>? = null

    fun getCoinList(): Observable<Array<SummaryCoinResponse>> {
        return cmcAPIService.coinListGET(0)
    }
}