package makes.flint.poh.factories

import makes.flint.poh.configuration.POHSettings
import makes.flint.poh.data.Summary
import makes.flint.poh.data.trackerListItem.TrackerListItem
import java.math.BigDecimal
import javax.inject.Inject

/**
 * SummaryFactory
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SummaryFactory @Inject constructor() {

    fun makeSummaryFor(data: List<TrackerListItem>): Summary {
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

    internal fun makeEmptySummary(): Summary {
        val zero = BigDecimal.ZERO
        return Summary(zero, zero, zero, zero, zero, zero, mutableListOf())
    }

    private fun calculatePercentageChange(initialValue: BigDecimal, currentValue: BigDecimal): BigDecimal {
        val difference = currentValue.minus(initialValue)
        val movement = difference.divide(initialValue, POHSettings.roundingMode)
        return movement.setScale(4, POHSettings.roundingMode)
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
