package makes.flint.poh.data.response

/**
 * ResponseList
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface ResponseList {
     var responseState: String
     var message: String
     var baseImageURL: String
     var baseURL: String
     var data: Map<String, CoinResponse>
}