package makes.flint.poh.data.response

import com.google.gson.annotations.SerializedName

/**
 * CoinResponse
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class APICoinResponse: CoinResponse {
    // Properties

    @SerializedName("Name")
    override lateinit var name: String

    @SerializedName("Id")
    override lateinit var id: String

    @SerializedName("Url")
    override lateinit var url: String

    @SerializedName("ImageUrl")
    override lateinit var imageUrl: String

    @SerializedName("CoinName")
    override lateinit var coinName: String

    @SerializedName("FullName")
    override lateinit var fullName: String

    @SerializedName("Algorithm")
    override lateinit var algorithm: String

    @SerializedName("ProofType")
    override lateinit var proofType: String

    @SerializedName("SortOrder")
    override lateinit var sortOrder: String
}
