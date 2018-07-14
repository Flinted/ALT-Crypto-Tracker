package did.chris.alt.data.services.interfaces

import did.chris.alt.data.response.coinSummary.SummaryCoinResponse
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * CryptoCompareAPIServiceInterface
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

interface CMCAPIService {

    @GET("/v1/ticker")
    fun coinListGET(
            @Query("limit") limit: Int
    ): Observable<Array<SummaryCoinResponse>>

    @GET("/v1/global")
    fun marketSummaryGET(): Observable<MarketSummaryResponse>
}
