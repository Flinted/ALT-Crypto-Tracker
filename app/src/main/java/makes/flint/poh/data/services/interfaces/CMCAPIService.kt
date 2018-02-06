package makes.flint.poh.data.services.interfaces

import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * CryptoCompareAPIServiceInterface
 * Copyright © 2018 Flint Makes. All rights reserved.
 */

interface CMCAPIService {

    @GET("/v1/ticker")
    fun coinListGET(
            @Query("limit") limit: Int
    ): Observable<Array<SummaryCoinResponse>>
}
