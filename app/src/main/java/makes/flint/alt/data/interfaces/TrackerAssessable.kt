package makes.flint.alt.data.interfaces

import makes.flint.alt.configuration.ALTSharedPreferences
import makes.flint.alt.data.coinListItem.*
import java.math.BigDecimal

/**
 * TrackerAssessable
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface TrackerAssessable {

    // Properties

    var percentageChange: BigDecimal
}

fun TrackerAssessable.assessChange(): Int {
    val floatChange = percentageChange.toFloat()
    return when {
        floatChange > ALTSharedPreferences.getValueForTrackerThreshold(0) -> CHANGE_UP_EXTREME
        floatChange > ALTSharedPreferences.getValueForTrackerThreshold(1) -> CHANGE_UP_SIGNIFICANT
        floatChange > ALTSharedPreferences.getValueForTrackerThreshold(2) -> CHANGE_UP_MODERATE
        floatChange < ALTSharedPreferences.getValueForTrackerThreshold(5) -> CHANGE_DOWN_EXTREME
        floatChange < ALTSharedPreferences.getValueForTrackerThreshold(4) -> CHANGE_DOWN_SIGNIFICANT
        floatChange < ALTSharedPreferences.getValueForTrackerThreshold(3) -> CHANGE_DOWN_MODERATE
        floatChange < 0f                                                  -> CHANGE_STATIC_NEGATIVE
        else                                                              -> CHANGE_STATIC_POSITIVE
    }
}