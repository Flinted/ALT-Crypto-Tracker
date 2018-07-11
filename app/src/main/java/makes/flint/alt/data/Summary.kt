package makes.flint.alt.data

import makes.flint.alt.configuration.ALTSharedPreferences
import makes.flint.alt.data.interfaces.TrackerAssessable
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.utility.NumberFormatter
import java.math.BigDecimal

/**
 * Summary
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class Summary(
    private val initialValue: BigDecimal,
    private val amountSpent: BigDecimal,
    private val amountSold: BigDecimal,
    private val currentFiatValue: BigDecimal,
    private val currentBTCValue: BigDecimal,
    override var percentageChange: BigDecimal,
    private val data: List<TrackerListItem>
) : TrackerAssessable {

    // Properties

    private val currentStanding = amountSold + amountSpent

    // Public Functions

    fun initialValueFormatted(): String {
        val rounded = initialValue.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun amountSpentFormatted(): String {
        val rounded = amountSpent.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun amountSoldFormatted(): String {
        val rounded = amountSold.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun currentStandingFormatted(): String {
        val rounded = currentStanding.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun currentValueFiatFormatted(): String {
        val rounded = currentFiatValue.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    fun currentValueBTCFormatted(): String {
        val rounded = currentBTCValue.setScale(8, ALTSharedPreferences.getRoundingMode())
        return "B${NumberFormatter.format(rounded, 8)}"
    }

    fun percentageChangeFormatted(): String {
        val rounded = percentageChange.setScale(4, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatPercentage(rounded, 2)
    }

    fun getTrackerEntries() = data
}
