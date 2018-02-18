package makes.flint.alt.configuration

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import makes.flint.alt.ui.interfaces.SORT_RANK
import java.util.*

/**
 * SettingsData
 * Copyright © 2018 FlintMakes. All rights reserved.
 */
open class SettingsData : RealmObject() {

    @PrimaryKey
    private var id = UUID.randomUUID().toString()

    var currencyCode = "USD"
    var symbol = "$"

    var refreshGap = 5L
    var exchangeFilter = "CCCAGG"
    var syncLimit = 1000
    var startingScreen = START_TRACKER

    var defaultSort = SORT_RANK

    var marketUp3 = 50f
    var marketUp2 = 15f
    var marketUp1 = 5f
    var marketDown1 = -5f
    var marketDown2 = -15f
    var marketDown3 = -50f

    var trackerUp3 = 1.5f
    var trackerUp2 = 0.5f
    var trackerUp1 = 0.2f
    var trackerDown1 = -0.2f
    var trackerDown2 = -0.4f
    var trackerDown3 = -0.6f
}