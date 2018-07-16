package did.chris.alt.data.coinListItem

import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.data.response.CoinResponse

class ChangeData(coinResponse: CoinResponse) {

    // Properties
    internal var percentChange1H = coinResponse.percentChange1H
    internal var percentChange24H = coinResponse.percentChange24H
    internal var percentChange7D = coinResponse.percentChange7D
    internal val status1H = assessChange(percentChange1H)
    internal val status24H = assessChange(percentChange24H)
    internal val status7D = assessChange(percentChange7D)

    // Internal Functions
    internal fun change1HFormatted() = "${percentChange1H.toString()}%"

    internal fun change24HFormatted() = "${percentChange24H.toString()}%"

    internal fun change7DFormatted() = "${percentChange7D.toString()}%"

    // Private Functions
    private fun assessChange(change: String?): Int {
        change ?: return CHANGE_UNKNOWN
        val floatChange = change.toFloat()
        return when {
            isGreaterThanMarketCutOff(floatChange, 0) -> CHANGE_UP_EXTREME
            isGreaterThanMarketCutOff(floatChange, 1) -> CHANGE_UP_SIGNIFICANT
            isGreaterThanMarketCutOff(floatChange, 2) -> CHANGE_UP_MODERATE
            isLessThanMarketCutOff(floatChange, 5)    -> CHANGE_DOWN_EXTREME
            isLessThanMarketCutOff(floatChange, 4)    -> CHANGE_DOWN_SIGNIFICANT
            isLessThanMarketCutOff(floatChange, 3)    -> CHANGE_DOWN_MODERATE
            floatChange < 0f                          -> CHANGE_STATIC_NEGATIVE
            else                                      -> CHANGE_STATIC_POSITIVE
        }
    }

    private fun isGreaterThanMarketCutOff(change: Float, marketKey: Int): Boolean {
        return change > ALTSharedPreferences.getValueForMarketThreshold(marketKey)
    }

    private fun isLessThanMarketCutOff(change: Float, marketKey: Int): Boolean {
        return change < ALTSharedPreferences.getValueForMarketThreshold(marketKey)
    }
}
