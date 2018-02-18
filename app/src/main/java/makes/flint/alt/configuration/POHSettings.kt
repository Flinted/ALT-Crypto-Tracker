package makes.flint.alt.configuration

import makes.flint.alt.ui.interfaces.SORT_RANK
import java.math.RoundingMode

/**
 * POHSettings
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
const val START_MARKET = "StartMarket"
const val START_TRACKER = "StartTracker"

object POHSettings {

    // Formatting

    var currency = "USD"
    var symbol = "$"
    var roundingMode = RoundingMode.HALF_EVEN

    // General

    var firstLoad = true
    var refreshGap = 5L
    var exchange = "CCCAGG"
    var limit = 1000
    var startingScreen = START_TRACKER

    //Sorting

    var sortPreference = SORT_RANK

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

    fun updateSettings(settings: SettingsData) {
        currency = settings.currencyCode
        symbol = settings.symbol
        sortPreference = settings.defaultSort
        startingScreen = settings.startingScreen
        exchange = settings.exchangeFilter
        limit = settings.syncLimit
        refreshGap = settings.refreshGap
        changeUp1 = settings.marketUp1
        changeUp2 = settings.marketUp2
        changeUp3 = settings.marketUp3
        changeDown1 = settings.marketDown1
        changeDown2 = settings.marketDown2
        changeDown3 = settings.marketDown3
        trackerChangeUp1 = settings.trackerUp1
        trackerChangeUp2 = settings.trackerUp2
        trackerChangeUp3 = settings.trackerUp3
        trackerChangeDown1 = settings.trackerDown1
        trackerChangeDown2 = settings.trackerDown2
        trackerChangeDown3 = settings.trackerDown3
    }

}
