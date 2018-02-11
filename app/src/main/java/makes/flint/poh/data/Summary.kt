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
              private val amountSpent: BigDecimal,
              private val amountSold: BigDecimal,
              private val currentFiatValue: BigDecimal,
              private val currentBTCValue: BigDecimal,
              override var percentageChange: BigDecimal,
              private val data: List<TrackerListItem>) : TrackerAssessable {

    private val currentStanding = amountSold + amountSpent

    fun initialValueFormatted(): String {
        val rounded = initialValue.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun amountSpentFormatted(): String {
        val rounded = amountSpent.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun amountSoldFormatted(): String {
        val rounded = amountSold.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun currentStandingFormatted(): String {
        val rounded = currentStanding.setScale(2, POHSettings.roundingMode)
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

    fun getTrackerEntries() = data
}
