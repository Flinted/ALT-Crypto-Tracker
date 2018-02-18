package makes.flint.alt.data.interfaces

import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.coinListItem.*
import java.math.BigDecimal

/**
 * TrackerAssessable
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface TrackerAssessable {
    var percentageChange : BigDecimal
}

fun TrackerAssessable.assessChange(): Int {
    val floatChange = percentageChange.toFloat()
    return when {
        floatChange > POHSettings.trackerChangeUp3 -> CHANGE_UP_EXTREME
        floatChange > POHSettings.trackerChangeUp2 -> CHANGE_UP_SIGNIFICANT
        floatChange > POHSettings.trackerChangeUp1 -> CHANGE_UP_MODERATE
        floatChange < POHSettings.trackerChangeDown3 -> CHANGE_DOWN_EXTREME
        floatChange < POHSettings.trackerChangeDown2 -> CHANGE_DOWN_SIGNIFICANT
        floatChange < POHSettings.trackerChangeDown1 -> CHANGE_DOWN_MODERATE
        floatChange < 0f -> CHANGE_STATIC_NEGATIVE
        else -> CHANGE_STATIC_POSITIVE
    }
}