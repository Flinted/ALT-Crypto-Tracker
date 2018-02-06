package makes.flint.poh.data.coinListItem.marketData

import makes.flint.poh.data.coinListItem.MarketListItem
import java.text.NumberFormat

/**
 * MarketData
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
class MarketData(oneHourChange: Float,
                 twentyFourHourChange: Float,
                 sevenDayChange: Float,
                 val numberItems: Int) : MarketListItem {

    private val oneHourAverage = oneHourChange / numberItems
    private val twentyFourHourAverage = twentyFourHourChange / numberItems
    private val sevenDayAverage = sevenDayChange / numberItems
    private val formatter = NumberFormat.getNumberInstance()

    fun oneHourAverageFormatted(): String {
        formatter.maximumFractionDigits = 2
        return formatter.format(oneHourAverage)
    }

    fun twentyFourHourAverageFormatted(): String {
        formatter.maximumFractionDigits = 2
        return formatter.format(twentyFourHourAverage)
    }

    fun sevenDayAverageFormatted(): String {
        formatter.maximumFractionDigits = 2
        return formatter.format(sevenDayAverage)
    }
}
