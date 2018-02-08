package makes.flint.poh.configuration

import makes.flint.poh.ui.interfaces.SORT_RANK
import java.math.RoundingMode

/**
 * POHSettings
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
const val START_MARKET = "StartMarket"
const val START_TRACKER = "StartTracker"

object POHSettings {

    private val settingsKey = "POHSettings"

    // Formatting

    var currency = "USD"
    var symbol = "$"
    var roundingMode = RoundingMode.HALF_EVEN

    // General

    var refreshGap = 5L
    var exchange = "CCCAGG"
    var limit = 750
    var startingScreen = START_TRACKER

    //Sorting

    var sortPreference = SORT_RANK
    var sortReversed = false

    // Market Thresholds

    var changeUp3 = 50f
    var changeUp2 = 15f
    var changeUp1 = 5f
    var changeDown1 = -5f
    var changeDown2 = -15f
    var changeDown3 = -50f

    // Portfolio Thresholds

    var trackerChangeUp3 = 1.5f
    var trackerChangeUp2 = 0.1f
    var trackerChangeUp1 = 0.2f
    var trackerChangeDown1 = -0.2f
    var trackerChangeDown2 = -0.4f
    var trackerChangeDown3 = -0.6f
}
