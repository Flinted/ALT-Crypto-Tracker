package makes.flint.poh.data.services.interfaces

import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import retrofit2.http.GET
import rx.Observable

/**
 * CryptoCompareAPIServiceInterface
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface CMCAPIService {

    @GET("/v1/ticker")
    fun coinListGET(): Observable<Array<SummaryCoinResponse>>
}