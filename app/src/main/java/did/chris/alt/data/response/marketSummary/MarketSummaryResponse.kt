package did.chris.alt.data.response.marketSummary

import com.google.gson.annotations.SerializedName
import did.chris.alt.data.coinListItem.marketData.MarketData
import did.chris.alt.utility.NumberFormatter
import java.math.BigDecimal

class MarketSummaryResponse {

    // Properties
    @SerializedName("total_market_cap_usd")
    private var marketCapUSD: Double = 0.0

    @SerializedName("total_24h_volume_usd")
    private var volume24HUSD: Double = 0.0

    internal lateinit var marketData: MarketData

    // Internal Functions
    internal fun marketCapUSDFormatted(): String {
        val bigDecimalMarketCap = BigDecimal(marketCapUSD)
        return NumberFormatter.formatCurrency(bigDecimalMarketCap, 0, 0)
    }

    internal fun volume24HUSDFormatted(): String {
        val bigDecimalVolume = BigDecimal(volume24HUSD)
        return NumberFormatter.formatCurrency(bigDecimalVolume, 0, 0)
    }
}
