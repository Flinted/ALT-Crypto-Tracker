package makes.flint.alt.data.response.histoResponse

import com.google.gson.annotations.SerializedName

/**
 * HistoricalDataUnitResponse
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
class HistoricalDataUnitResponse {

    @SerializedName("time")
    var time: Float? = null

    @SerializedName("open")
    var open: Float? = null

    @SerializedName("close")
    var close: Float? = null

    @SerializedName("high")
    var high: Float? = null

    @SerializedName("low")
    var low: Float? = null

    @SerializedName("volumeFrom")
    private var volumeFrom: Float? = null

    @SerializedName("volumeTo")
    private var volumeTo: Float? = null
}
