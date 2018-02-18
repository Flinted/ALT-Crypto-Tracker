package makes.flint.alt.data.snapshot

import makes.flint.alt.data.TimeStamp
import java.math.BigDecimal

/**
 * Snapshot
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class Snapshot {
    private val dateTaken = TimeStamp()
    private lateinit var valueUSD: BigDecimal
    private lateinit var valueBTC: BigDecimal
    private var holdings: MutableList<SnapshotEntry> = mutableListOf()
}