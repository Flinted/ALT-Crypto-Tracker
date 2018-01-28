package makes.flint.poh.data.response.coinSummary

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * CoinResponse
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
const val CHANGE_UP_SIGNIFICANT = 0
const val CHANGE_UP_MODERATE = 1
const val CHANGE_STATIC = 2
const val CHANGE_DOWN_MODERATE = 3
const val CHANGE_DOWN_SIGNIFICANT = 4

class SummaryCoinResponse {
    // Properties

    @SerializedName("name") lateinit var name: String

    @SerializedName("id") lateinit var id: String

    @SerializedName("symbol") lateinit var symbol: String

    @SerializedName("rank") lateinit var rank: String

    @SerializedName("price_usd") lateinit var priceUSD: String

    @SerializedName("price_btc") lateinit var priceBTC: String

    @SerializedName("24h_volume_usd") lateinit var volume24hUSD: String

    @SerializedName("market_cap_usd") lateinit var marketCapUSD: String

    @SerializedName("available_supply") lateinit var availableSupply: String

    @SerializedName("total_supply") lateinit var totalSupply: String

    @SerializedName("percent_change_1h") lateinit var percentChange1h: String

    @SerializedName("percent_change_24h") lateinit var percentChange24h: String

    @SerializedName("percent_change_7d") lateinit var percentChange7d: String

    internal fun rankInt() = rank.toInt()

    internal fun stabilisedPrice(): String {
        val bdMarketCapUSD = BigDecimal(marketCapUSD)
        val bdBillion = BigDecimal("1000000000")
        val billionCoinPrice = bdMarketCapUSD.divide(bdBillion)
        return billionCoinPrice.toPlainString()
    }

    fun oneHourStatus(): Int {
        val floatChange = percentChange1h.toFloat()
        val changeState = assessChange(floatChange)
        return changeState
    }
    fun twentyFourHourStatus(): Int {
        val floatChange = percentChange24h.toFloat()
        val changeState = assessChange(floatChange)
        return changeState
    }

    fun sevenDayStatus(): Int {
        val floatChange = percentChange7d.toFloat()
        val changeState = assessChange(floatChange)
        return changeState
    }

    private fun assessChange(floatChange: Float): Int {
        return when {
            floatChange in 5f..15f -> CHANGE_UP_MODERATE
            floatChange > 15f -> CHANGE_UP_SIGNIFICANT
            floatChange in -15f..-5f -> CHANGE_DOWN_MODERATE
            floatChange < -15f -> CHANGE_DOWN_SIGNIFICANT
            else -> CHANGE_STATIC
        }
    }
}

internal fun SummaryCoinResponse.compareRank(comparator: SummaryCoinResponse): Int {
    if (this.rankInt() == comparator.rankInt()) {
        return 0
    }
    if (this.rankInt() < comparator.rankInt()) {
        return -1
    }
    return 1
}


