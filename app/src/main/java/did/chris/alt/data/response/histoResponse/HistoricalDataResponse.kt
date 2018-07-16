package did.chris.alt.data.response.histoResponse

import com.google.gson.annotations.SerializedName

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

