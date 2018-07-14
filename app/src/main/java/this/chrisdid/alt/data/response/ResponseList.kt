package this.chrisdid.alt.data.response

import this.chrisdid.alt.data.response.coinSummary.SummaryCoinResponse

/**
 * ResponseList
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface ResponseList {

    // Properties

    var responseState: String
    var message: String
    var baseImageURL: String
    var baseURL: String
    var data: Map<String, SummaryCoinResponse>
}
