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

    //Properties

    @PrimaryKey
    private var id = UUID.randomUUID().toString()

    internal var currencyCode = "USD"
    internal var symbol = "$"

    internal var firstLoad = true
    internal var refreshGap = 5L
    internal var exchangeFilter = "CCCAGG"
    internal var syncLimit = 1000
    internal var startingScreen = START_TRACKER

    internal var defaultSort = SORT_RANK

    internal var marketUp3 = 50f
    internal var marketUp2 = 15f
    internal var marketUp1 = 5f
    internal var marketDown1 = -5f
    internal var marketDown2 = -15f
    internal var marketDown3 = -50f

    internal var trackerUp3 = 1.5f
    internal var trackerUp2 = 0.5f
    internal var trackerUp1 = 0.2f
    internal var trackerDown1 = -0.2f
    internal var trackerDown2 = -0.4f
    internal var trackerDown3 = -0.6f
}