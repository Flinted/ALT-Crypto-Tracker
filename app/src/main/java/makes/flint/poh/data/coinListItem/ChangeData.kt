package makes.flint.poh.data.coinListItem

import makes.flint.poh.data.response.CoinResponse

/**
 * ChangeData
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class ChangeData(coinResponse: CoinResponse) {

    internal var percentChange1H = coinResponse.percentChange1H
    internal var percentChange24H = coinResponse.percentChange24H
    internal var percentChange7D = coinResponse.percentChange7D
    internal val status1H = assessChange(percentChange1H)
    internal val status24H = assessChange(percentChange24H)
    internal val status7D= assessChange(percentChange7D)

    private fun assessChange(change: String?): Int {
        change ?: return CHANGE_UNKNOWN
        val floatChange = change.toFloat()
        return when {
            floatChange > 50f -> CHANGE_UP_EXTREME
            floatChange > 15f -> CHANGE_UP_SIGNIFICANT
            floatChange in 5f..15f -> CHANGE_UP_MODERATE
            floatChange < -50f -> CHANGE_DOWN_EXTREME
            floatChange < -15f -> CHANGE_DOWN_SIGNIFICANT
            floatChange in -15f..-5f -> CHANGE_DOWN_MODERATE
            floatChange < 0f -> CHANGE_STATIC_NEGATIVE
            else -> CHANGE_STATIC_POSITIVE
        }
    }
}