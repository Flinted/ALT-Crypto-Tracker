package did.chris.alt.data.response

import did.chris.alt.data.response.coinSummary.SummaryCoinResponse

interface ResponseList {

    // Properties
    var responseState: String
    var message: String
    var baseImageURL: String
    var baseURL: String
    var data: Map<String, SummaryCoinResponse>
}
