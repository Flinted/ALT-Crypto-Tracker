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
    override lateinit var baseImageURL: String
    override lateinit var baseURL: String
    override lateinit var data: Map<String, CoinResponse>
}
