package did.chris.alt.data.response.coinSummary

import com.google.gson.annotations.SerializedName
import did.chris.alt.data.response.CoinResponse
import java.math.BigDecimal

/**
 * CoinResponse
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

class SummaryCoinResponse : CoinResponse {

    // Properties

    @SerializedName("name")
    override lateinit var name: String

    @SerializedName("id")
    override lateinit var id: String

    @SerializedName("symbol")
    override lateinit var symbol: String

    @SerializedName("rank")
    private lateinit var rank: String

    @SerializedName("price_usd")
    private var priceUSD: String? = null

    @SerializedName("price_btc")
    private var priceBTC: String? = null

    @SerializedName("24h_volume_usd")
    private var volume24hUSD: String? = null

    @SerializedName("market_cap_usd")
    private var marketCapUSD: String? = null

    @SerializedName("available_supply")
    private var availableSupply: String? = null

    @SerializedName("total_supply")
    private var totalSupply: String? = null

    @SerializedName("percent_change_1h")
    override var percentChange1H: String? = null

    @SerializedName("percent_change_24h")
    override var percentChange24H: String? = null

    @SerializedName("percent_change_7d")
    override var percentChange7D: String? = null

    // Overrides

    override fun provideRank() = rank.toInt()

    override fun providePriceUSD(): BigDecimal? {
        priceUSD ?: return null
        return BigDecimal(priceUSD)
    }

    override fun providePriceBTC(): BigDecimal? {
        priceBTC ?: return null
        return BigDecimal(priceBTC)
    }

    override fun provideVolume24Hour(): BigDecimal? {
        volume24hUSD ?: return null
        return BigDecimal(volume24hUSD)
    }

    override fun provideMarketCapUSD(): BigDecimal? {
        marketCapUSD ?: return null
        return BigDecimal(marketCapUSD)
    }

    override fun provideAvailableSupply() = availableSupply

    override fun provideTotalSupply() = totalSupply
}
