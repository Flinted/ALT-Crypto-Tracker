package makes.flint.alt.data.response

import makes.flint.alt.data.response.coinSummary.SummaryCoinResponse

/**
 * ResponseList
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface ResponseList {
     var responseState: String
     var message: String
     var baseImageURL: String
     var baseURL: String
     var data: Map<String, SummaryCoinResponse>
}
