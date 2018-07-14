package did.chris.alt.data.coinListItem

import did.chris.alt.data.response.CoinResponse
import did.chris.alt.utility.NumberFormatter
import java.math.BigDecimal
import java.math.RoundingMode

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

    // Private Functions

    private fun priceUSDFormatted(): String {
        priceUSD ?: return "No Price"
        return NumberFormatter.formatCurrencyAutomaticDigit(priceUSD)
    }

    private fun createStabilisedPrice(): String {
        marketCapUSD ?: return NO_INFORMATION
        val billion = BigDecimal(ONE_BILLION)
        val billionCoinPrice = marketCapUSD.divide(billion)
        val roundedBillionCoinPrice = billionCoinPrice.setScale(2, RoundingMode.HALF_EVEN)
        return NumberFormatter.formatCurrency(roundedBillionCoinPrice, 2, 2)
    }

    private fun priceBTCFormatted(): String {
        priceBTC?.let {
            return NumberFormatter.format(it, 8)
        }
        return "Unknown"
    }

    private fun marketCapFormatted(): String {
        marketCapUSD?.let {
            return NumberFormatter.formatCurrency(it, 0, 0)
        }
        return "Unknown"
    }

    // Companion

    companion object {
        val decimal3Threshold = BigDecimal(THRESHOLD_3_DECIMAL)
        val decimal4Threshold = BigDecimal(THRESHOLD_4_DECIMAL)
        val decimal5Threshold = BigDecimal(THRESHOLD_5_DECIMAL)
    }
}
