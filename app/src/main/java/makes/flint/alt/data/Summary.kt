package makes.flint.alt.data

import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.interfaces.TrackerAssessable
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.utility.NumberFormatter
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

    // Properties

    private val currentStanding = amountSold + amountSpent

    // Internal Functions

    internal fun initialValueFormatted(): String {
        val rounded = initialValue.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun amountSpentFormatted(): String {
        val rounded = amountSpent.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun amountSoldFormatted(): String {
        val rounded = amountSold.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun currentStandingFormatted(): String {
        val rounded = currentStanding.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun currentValueFiatFormatted(): String {
        val rounded = currentFiatValue.setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun currentValueBTCFormatted(): String {
        val rounded = currentBTCValue.setScale(8, RoundingMode.HALF_EVEN)
        return "B${NumberFormatter.format(rounded, 8)}"
    }

    internal fun percentageChangeFormatted(): String {
        val rounded = percentageChange.setScale(4, POHSettings.roundingMode)
        return NumberFormatter.formatPercentage(rounded, 2)
    }

    internal fun getTrackerEntries() = data
}
