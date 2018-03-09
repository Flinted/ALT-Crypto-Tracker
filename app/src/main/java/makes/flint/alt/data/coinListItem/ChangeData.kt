package makes.flint.alt.data.coinListItem

import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.response.CoinResponse

/**
 * ChangeData
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
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
            floatChange > POHSettings.changeUp3 -> CHANGE_UP_EXTREME
            floatChange > POHSettings.changeUp2 -> CHANGE_UP_SIGNIFICANT
            floatChange > POHSettings.changeUp1 -> CHANGE_UP_MODERATE
            floatChange < POHSettings.changeDown3 -> CHANGE_DOWN_EXTREME
            floatChange < POHSettings.changeDown2 -> CHANGE_DOWN_SIGNIFICANT
            floatChange < POHSettings.changeDown1 -> CHANGE_DOWN_MODERATE
            floatChange < 0f -> CHANGE_STATIC_NEGATIVE
            else -> CHANGE_STATIC_POSITIVE
        }
    }
}
