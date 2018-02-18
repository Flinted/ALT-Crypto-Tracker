package makes.flint.alt.data.response.marketSummary

import com.google.gson.annotations.SerializedName
import makes.flint.alt.data.coinListItem.marketData.MarketData

/**
 * MarketSummaryResponse
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketSummaryResponse {
    @SerializedName("total_market_cap_usd")
    private var marketCapUSD: Double = 0.0

    @SerializedName("total_24h_volume_usd")
    private var volume24HUSD: Double = 0.0

    @SerializedName("bitcoin_percentage_of_market_cap")
    private var bitcoinDominancePercent: Double = 0.0

    internal lateinit var marketData: MarketData
}