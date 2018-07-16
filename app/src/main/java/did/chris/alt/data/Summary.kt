package did.chris.alt.data

import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.data.interfaces.TrackerAssessable
import did.chris.alt.data.trackerListItem.TrackerListItem
import did.chris.alt.utility.NumberFormatter
import java.math.BigDecimal

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

    // Internal Functions
    internal fun initialValueFormatted(): String {
        val rounded = initialValue.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun amountSpentFormatted(): String {
        val rounded = amountSpent.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun amountSoldFormatted(): String {
        val rounded = amountSold.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun profitLossFormatted(): String {
        val difference = currentFiatValue.minus(initialValue)
        return NumberFormatter.formatCurrency(difference, 2)
    }

    internal fun currentStandingFormatted(): String {
        val rounded = currentStanding.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun currentValueFiatFormatted(): String {
        val rounded = currentFiatValue.setScale(2, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatCurrency(rounded, 2)
    }

    internal fun currentValueBTCFormatted(): String {
        val rounded = currentBTCValue.setScale(8, ALTSharedPreferences.getRoundingMode())
        return "B${NumberFormatter.format(rounded, 8)}"
    }

    internal fun percentageChangeFormatted(): String {
        val rounded = percentageChange.setScale(4, ALTSharedPreferences.getRoundingMode())
        return NumberFormatter.formatPercentage(rounded, 2)
    }

    internal fun getTrackerEntries() = data
}
