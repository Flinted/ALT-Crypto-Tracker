package makes.flint.poh.data.services.interfaces

import makes.flint.poh.data.response.APIResponseList
import retrofit2.http.GET
import rx.Observable

/**
 * CryptoCompareAPIServiceInterface
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface CryptoCompareAPIService {

    @GET("/data/all/coinlist")
    fun coinListGET(): Observable<APIResponseList>
}