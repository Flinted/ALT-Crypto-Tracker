package makes.flint.poh.data.response

import com.google.gson.annotations.SerializedName

/**
 * APIResponseList
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class APIResponseList: ResponseList {

    // Properties
    @SerializedName("Response")
    override lateinit var responseState: String

    @SerializedName("Message")
    override lateinit var message: String

    @SerializedName("BaseImageUrl")
    override lateinit var baseImageURL: String

    @SerializedName("BaseLinkUrl")
    override lateinit var baseURL: String

    @SerializedName("Data")
    override lateinit var data: Map<String, CoinResponse>

}
