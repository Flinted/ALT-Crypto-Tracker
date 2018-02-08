package makes.flint.poh.data.coinListItem

import makes.flint.poh.configuration.POHSettings
import makes.flint.poh.data.response.CoinResponse
import makes.flint.poh.utility.NumberFormatter
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * PriceData
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
private const val ONE_BILLION = "1000000000"
private const val NO_INFORMATION = ""
private const val THRESHOLD_3_DECIMAL = "0.99"
private const val THRESHOLD_4_DECIMAL = "0.099"
private const val THRESHOLD_5_DECIMAL = "0.0099"

class PriceData(coinResponse: CoinResponse) {

    // Properties
    internal val priceUSD = coinResponse.providePriceUSD()
    internal val priceBTC = coinResponse.providePriceBTC()
    internal val marketCapUSD = coinResponse.provideMarketCapUSD()
    internal val stabilisedPrice = createStabilisedPrice()
    internal val priceUSDFormatted = priceUSDFormatted()
    internal val priceBTCFormatted = priceBTCFormatted()
    internal val marketCapFormatted = marketCapFormatted()

    private fun priceUSDFormatted(): String {
        priceUSD ?: return "No Price"
        val roundingMode = POHSettings.roundingMode
        val roundedNumber = when {
            priceUSD > decimal3Threshold -> priceUSD.setScale(2, roundingMode)
            priceUSD > decimal4Threshold -> priceUSD.setScale(3, roundingMode)
            priceUSD > decimal5Threshold -> priceUSD.setScale(4, roundingMode)
            else -> priceUSD.setScale(7, roundingMode)
        }
        return NumberFormatter.formatCurrency(roundedNumber)
    }

    // Private Functions
    private fun createStabilisedPrice(): String {
        marketCapUSD ?: return NO_INFORMATION
        val billion = BigDecimal(ONE_BILLION)
        val billionCoinPrice = marketCapUSD.divide(billion)
        val roundedBillionCoinPrice = billionCoinPrice.setScale(2, RoundingMode.HALF_EVEN)
        return NumberFormatter.formatCurrency(roundedBillionCoinPrice, 2)
    }

    companion object {
        val decimal3Threshold = BigDecimal(THRESHOLD_3_DECIMAL)
        val decimal4Threshold = BigDecimal(THRESHOLD_4_DECIMAL)
        val decimal5Threshold = BigDecimal(THRESHOLD_5_DECIMAL)
    }

    private fun priceBTCFormatted(): String {
        priceBTC?.let {
            return NumberFormatter.format(it, 8)
        }
        return "Unknown"
    }

    private fun marketCapFormatted(): String {
        marketCapUSD?.let {
            return NumberFormatter.formatCurrency(it, 0)
        }
        return "Unknown"
    }
}
