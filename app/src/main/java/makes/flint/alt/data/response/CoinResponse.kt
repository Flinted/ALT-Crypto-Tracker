package makes.flint.alt.data.response

import java.math.BigDecimal

/**
 * CoinResponse
 * Copyright © 2018 Flint Makes. All rights reserved.
 */

interface CoinResponse {

    // Properties

    var name: String
    var id: String
    var symbol: String
    var percentChange1H: String?
    var percentChange24H: String?
    var percentChange7D: String?

    // Functions

    fun provideRank(): Int
    fun providePriceUSD(): BigDecimal?
    fun providePriceBTC(): BigDecimal?
    fun provideVolume24Hour(): BigDecimal?
    fun provideMarketCapUSD(): BigDecimal?
    fun provideAvailableSupply(): String?
    fun provideTotalSupply(): String?
}