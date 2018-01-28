package makes.flint.poh.data.coinListItem

import makes.flint.poh.data.response.CoinResponse
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * PriceData
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
private const val ONE_BILLION = "1000000000"
private const val NO_INFORMATION = "No Information"
class PriceData(coinResponse: CoinResponse) {

    // Properties
    internal val priceUSD = coinResponse.providePriceUSD()
    internal val priceBTC = coinResponse.providePriceBTC()
    internal val marketCapUSD = coinResponse.provideMarketCapUSD()
    internal val stabilisedPrice = createStabilisedPrice()

    // Private Functions
    private fun createStabilisedPrice(): String {
        marketCapUSD ?: return NO_INFORMATION
        val billion = BigDecimal(ONE_BILLION)
        val billionCoinPrice = marketCapUSD.divide(billion)
        val roundedBillionCoinPrice = billionCoinPrice.setScale(2, RoundingMode.HALF_EVEN)
        return "$" + roundedBillionCoinPrice.toPlainString()
    }
}