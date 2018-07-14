package this.chrisdid.alt.data.snapshot

import this.chrisdid.alt.data.TimeStamp
import this.chrisdid.alt.utility.NumberFormatter
import java.math.BigDecimal

/**
 * SnapShot
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class SnapShot(totalUSD: BigDecimal, totalBTC: BigDecimal, snapShotEntries: List<SnapShotEntry>) {

    // Properties

    internal val dateTaken = TimeStamp()
    internal val valueUSD = totalUSD
    internal val valueBTC = totalBTC
    internal val holdings = snapShotEntries

    // Internal Functions

    internal fun valueBTCFormatted(): String {
        return NumberFormatter.format(valueBTC, 8)
    }

    internal fun valueUSDFormatted(): String {
        return NumberFormatter.formatCurrency(valueUSD, 2, 2)
    }
}