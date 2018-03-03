package makes.flint.alt.data.response.histoResponse

import com.google.gson.annotations.SerializedName

/**
 * HistoricalDataResponse
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
class HistoricalDataResponse {

    // Properties

    @SerializedName("Response")
    internal var response: String? = null

    @SerializedName("Message")
    internal var message: String? = null

    @SerializedName("Aggregated")
    internal var aggregated: Boolean? = null

    @SerializedName("TimeTo")
    internal var timeTo: String? = null

    @SerializedName("TimeFrom")
    internal var timeFrom: String? = null

    @SerializedName("Data")
    internal var data: Array<HistoricalDataUnitResponse> = emptyArray()
}

