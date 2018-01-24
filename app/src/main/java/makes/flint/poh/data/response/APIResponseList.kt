package makes.flint.poh.data.response

/**
 * APIResponseList
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class APIResponseList: ResponseList {
    override lateinit var responseState: String
    override lateinit var message: String
    override lateinit var baseImageURL: String
    override lateinit var baseURL: String
    override lateinit var data: Map<String, CoinResponse>
}
