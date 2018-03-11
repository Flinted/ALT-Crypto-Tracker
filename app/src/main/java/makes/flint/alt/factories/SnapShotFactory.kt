package makes.flint.alt.factories

import makes.flint.alt.data.snapshot.SnapShot
import makes.flint.alt.data.snapshot.SnapShotEntry
import makes.flint.alt.data.trackerListItem.TrackerListItem
import java.math.BigDecimal
import javax.inject.Inject

/**
 * SnapShotFactory
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class SnapShotFactory @Inject constructor() {

    // Internal Functions

    internal fun makeSnapShot(trackerEntries: List<TrackerListItem>): SnapShot {
        val snapShotEntries = makeSnapShotEntries(trackerEntries)
        val totalUSD = makeTotalUSD(snapShotEntries)
        val totalBTC = makeTotalBTC(snapShotEntries)
        val snapShot = SnapShot(totalUSD, totalBTC, snapShotEntries)
        return snapShot
    }

    // Private Functions

    private fun makeTotalUSD(snapShotEntries: List<SnapShotEntry>): BigDecimal {
        return snapShotEntries.fold(BigDecimal.ZERO, { acc, snapShot ->
            acc.add(snapShot.valueUSD)
        })
    }

    private fun makeTotalBTC(snapShotEntries: List<SnapShotEntry>): BigDecimal {
        return snapShotEntries.fold(BigDecimal.ZERO, { acc, snapShot ->
            acc.add(snapShot.valueBTC)
        })
    }

    private fun makeSnapShotEntries(trackerEntries: List<TrackerListItem>): List<SnapShotEntry> {
        return trackerEntries.map { entry ->
            SnapShotEntry(entry)
        }
    }
}
