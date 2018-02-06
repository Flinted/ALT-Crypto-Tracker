package makes.flint.poh.data.response.histoResponse

import com.google.gson.annotations.SerializedName

/**
 * HistoricalDataResponse
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
class HistoricalDataResponse {

    @SerializedName("Response")
    var response: String? = null

    @SerializedName("Message")
    var message: String? = null

    @SerializedName("Aggregated")
    var aggregated: Boolean? = null

    @SerializedName("TimeTo")
    var timeTo: String? = null

    @SerializedName("TimeFrom")
    var timeFrom: String? = null

    @SerializedName("Data")
    var data: Array<HistoricalDataUnitResponse> = emptyArray()
}

