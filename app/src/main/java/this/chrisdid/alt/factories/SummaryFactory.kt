package this.chrisdid.alt.factories

import this.chrisdid.alt.configuration.ALTSharedPreferences
import this.chrisdid.alt.data.Summary
import this.chrisdid.alt.data.trackerListItem.TrackerListItem
import java.math.BigDecimal
import javax.inject.Inject

/**
 * SummaryFactory
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class SummaryFactory @Inject constructor() {

    // Internal Functions

    internal fun makeSummaryFor(data: List<TrackerListItem>): Summary {
        if (data.isEmpty()) {
            return makeEmptySummary()
        }
        val initialValue = getInitialFiatValueFor(data)
        val amountSpent = getAmountSpentFiat(data)
        val amountSold = getAmountSoldFiat(data)
        val currentValueUSD = getCurrentFiatValueFor(data)
        val currentValueBTC = getCurrentBTCValueFor(data)
        val percentageChange = calculatePercentageChange(initialValue, currentValueUSD)
        return Summary(
                initialValue,
                amountSpent,
                amountSold,
                currentValueUSD,
                currentValueBTC,
                percentageChange,
                data)
    }

    internal fun makeEmptySummary(): Summary {
        val zero = BigDecimal.ZERO
        return Summary(zero, zero, zero, zero, zero, zero, mutableListOf())
    }

    // Private Functions

    private fun getAmountSoldFiat(data: List<TrackerListItem>): BigDecimal {
        return data.fold(BigDecimal.ZERO) { acc, entry ->
            acc.add(entry.amountSold)
        }
    }

    private fun getAmountSpentFiat(data: List<TrackerListItem>): BigDecimal {
        return data.fold(BigDecimal.ZERO) { acc, entry ->
            acc.add(entry.amountSpent)
        }
    }

    private fun calculatePercentageChange(initialValue: BigDecimal, currentValue: BigDecimal): BigDecimal {
        if (initialValue == BigDecimal.ZERO) {
            return BigDecimal.ZERO
        }
        val difference = currentValue.minus(initialValue)
        val roundingMode = ALTSharedPreferences.getRoundingMode()
        val movement = difference.divide(initialValue, roundingMode)
        return movement.setScale(4, roundingMode)
    }

    private fun getInitialFiatValueFor(data: List<TrackerListItem>): BigDecimal {
        return data.fold(BigDecimal.ZERO) { acc, entry ->
            acc.add(entry.purchasePriceTotal)
        }
    }

    private fun getCurrentFiatValueFor(data: List<TrackerListItem>): BigDecimal {
        return data.fold(BigDecimal.ZERO) { acc, entry ->
            acc.add(entry.currentValueUSD)
        }
    }

    private fun getCurrentBTCValueFor(data: List<TrackerListItem>): BigDecimal {
        return data.fold(BigDecimal.ZERO) { acc, entry ->
            acc.add(entry.currentValueBTC)
        }
    }
}
