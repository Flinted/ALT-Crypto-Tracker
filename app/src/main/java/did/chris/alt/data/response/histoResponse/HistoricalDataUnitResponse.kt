package did.chris.alt.data.response.histoResponse

import com.google.gson.annotations.SerializedName

class HistoricalDataUnitResponse {

    // Properties
    @SerializedName("time")
    internal var time: Float? = null

    @SerializedName("open")
    internal var open: Float? = null

    @SerializedName("close")
    internal var close: Float? = null

    @SerializedName("high")
    internal var high: Float? = null

    @SerializedName("low")
    internal var low: Float? = null

    @SerializedName("volumeFrom")
    internal var volumeFrom: Float? = null

    @SerializedName("volumeTo")
    internal var volumeTo: Float? = null
}
