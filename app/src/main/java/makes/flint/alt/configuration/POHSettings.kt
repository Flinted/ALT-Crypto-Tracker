package makes.flint.alt.configuration

import makes.flint.alt.ui.interfaces.SORT_RANK
import java.math.RoundingMode

/**
 * POHSettings
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
const val START_MARKET = "StartMarket"
const val START_TRACKER = "StartTracker"

object POHSettings {

    // Formatting

    internal var currency = "USD"
    internal var symbol = "$"
    internal var roundingMode = RoundingMode.HALF_EVEN

    // General

    internal var firstLoad = true
    internal var refreshGap = 5L
    internal var exchange = "CCCAGG"
    internal var limit = 1000
    internal var startingScreen = START_TRACKER

    //Sorting

    internal var sortPreference = SORT_RANK

    // Market Thresholds

    internal var changeUp3 = 50f
    internal var changeUp2 = 15f
    internal var changeUp1 = 5f
    internal var changeDown1 = -5f
    internal var changeDown2 = -15f
    internal var changeDown3 = -50f

    // Portfolio Thresholds

    internal var trackerChangeUp3 = 1.5f
    internal var trackerChangeUp2 = 0.1f
    internal var trackerChangeUp1 = 0.2f
    internal var trackerChangeDown1 = -0.2f
    internal var trackerChangeDown2 = -0.4f
    internal var trackerChangeDown3 = -0.6f

    // Internal Functions

    internal fun updateSettings(settings: SettingsData) {
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
