package did.chris.alt.data.interfaces

import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.data.coinListItem.*
import java.math.BigDecimal

interface TrackerAssessable {

    // Properties
    var percentageChange: BigDecimal
}

// Internal Functions
internal fun TrackerAssessable.assessChange(): Int {
    val floatChange = percentageChange.toFloat()
    return when {
        isGreaterThanTrackerCutOff(floatChange, 0) -> CHANGE_UP_EXTREME
        isGreaterThanTrackerCutOff(floatChange, 1) -> CHANGE_UP_SIGNIFICANT
        isGreaterThanTrackerCutOff(floatChange, 2) -> CHANGE_UP_MODERATE
        isLessThanTrackerCutOff(floatChange, 5)    -> CHANGE_DOWN_EXTREME
        isLessThanTrackerCutOff(floatChange, 4)    -> CHANGE_DOWN_SIGNIFICANT
        isLessThanTrackerCutOff(floatChange, 3)    -> CHANGE_DOWN_MODERATE
        floatChange < 0f                           -> CHANGE_STATIC_NEGATIVE
        else                                       -> CHANGE_STATIC_POSITIVE
    }
}

// Private Functions
private fun isGreaterThanTrackerCutOff(change: Float, trackerKey: Int): Boolean {
    return change > ALTSharedPreferences.getValueForTrackerThreshold(trackerKey)
}

private fun isLessThanTrackerCutOff(change: Float, trackerKey: Int): Boolean {
    return change < ALTSharedPreferences.getValueForTrackerThreshold(trackerKey)
}