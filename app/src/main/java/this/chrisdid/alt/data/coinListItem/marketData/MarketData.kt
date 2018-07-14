package this.chrisdid.alt.data.coinListItem.marketData

import this.chrisdid.alt.data.coinListItem.MarketListItem
import this.chrisdid.alt.utility.NumberFormatter
import java.math.BigDecimal

/**
 * MarketData
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
class MarketData(twentyFourHourChange: Float,
                 sevenDayChange: Float,
                 internal val numberItems: Int) : MarketListItem {

    // Properties

    private val twentyFourHourAverage = twentyFourHourChange / numberItems
    private val sevenDayAverage = sevenDayChange / numberItems

    // Internal Functions

    internal fun twentyFourHourAverageFormatted(): String {
        val twentyFourHour = BigDecimal.valueOf(twentyFourHourAverage.toDouble())
        return "${NumberFormatter.format(twentyFourHour, 2)}%"
    }

    internal fun sevenDayAverageFormatted(): String {
        val sevenDay = BigDecimal.valueOf(sevenDayAverage.toDouble())
        return "${NumberFormatter.format(sevenDay, 2)}%"
    }
}
