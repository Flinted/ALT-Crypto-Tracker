package makes.flint.poh.data

import makes.flint.poh.configuration.POHSettings
import makes.flint.poh.data.interfaces.TrackerAssessable
import makes.flint.poh.data.trackerListItem.TrackerListItem
import makes.flint.poh.utility.NumberFormatter
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Summary
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class Summary(private val initialValue: BigDecimal,
              private val currentFiatValue: BigDecimal,
              private val currentBTCValue: BigDecimal,
              private val percentageChange: BigDecimal,
              private val data: List<TrackerListItem>) : TrackerAssessable {

    fun initialValueFormatted(): String {
        val rounded = initialValue.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun currentValueFiatFormatted(): String {
        val rounded = currentFiatValue.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun currentValueBTCFormatted(): String {
        val rounded = currentBTCValue.setScale(8, RoundingMode.HALF_EVEN)
        return "B${NumberFormatter.format(rounded, 8)}"
    }

    fun percentageChangeFormatted(): String {
        val rounded = percentageChange.setScale(4, POHSettings.roundingMode)
        return NumberFormatter.formatPercentage(rounded, 2)
    }

    override fun getPercentageChange() = percentageChange

    fun getTrackerEntries() = data

}

