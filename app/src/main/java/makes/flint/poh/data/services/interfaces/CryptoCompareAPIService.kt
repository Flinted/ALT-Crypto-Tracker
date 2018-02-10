package makes.flint.poh.data.services.interfaces

import makes.flint.poh.data.response.histoResponse.HistoricalDataResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * CryptoCompareAPIService
 * Copyright © 2018 Flint Makes. All rights reserved.
 */

interface CryptoCompareAPIService {

    @GET("/data/histominute")
    fun histoMinuteGET(
            @Query("fsym") fromSymbol: String,
            @Query("tsym") toSymbol: String,
            @Query("e") exchange: String
    ): Observable<HistoricalDataResponse>

    @GET("/data/histohour")
    fun histoHourGET(
            @Query("fsym") fromSymbol: String,
            @Query("tsym") toSymbol: String,
            @Query("e") exchange: String
    ): Observable<HistoricalDataResponse>

    @GET("/data/histoday")
    fun histoDayGET(
            @Query("fsym") fromSymbol: String,
            @Query("tsym") toSymbol: String,
            @Query("e") exchange: String,
            @Query("limit") limit: Int
    ): Observable<HistoricalDataResponse>
}
