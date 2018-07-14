package this.chrisdid.alt.data.snapshot

import this.chrisdid.alt.data.trackerListItem.TrackerListItem

/**
 * SnapShotEntry
 * Copyright © 2018 FlintMakes. All rights reserved.
 */
class SnapShotEntry(trackerEntry: TrackerListItem) {

    // Properties

    internal var name = trackerEntry.name
    internal var symbol = trackerEntry.symbol
    internal var quantity = trackerEntry.numberOwned
    internal var valueUSD = trackerEntry.currentValueUSD
    internal var valueBTC = trackerEntry.currentValueBTC
    internal var snapShotPrice = trackerEntry.getCurrentAssetPrice()
    internal var dollarCostAverage = trackerEntry.dollarCostAverage
    internal var percentageChange = trackerEntry.percentageChange
}