package makes.flint.alt.data.snapshot

import java.math.BigDecimal

/**
 * SnapshotEntry
 * Copyright © 2018 FlintMakes. All rights reserved.
 */
class SnapshotEntry {
    private lateinit var name: String
    private lateinit var symbol: String
    private lateinit var quantity: BigDecimal
    private lateinit var valueUSD: BigDecimal
    private lateinit var valueBTC: BigDecimal
    private lateinit var snapShotPrice: BigDecimal
    private lateinit var dollarCostAverage: BigDecimal
    private lateinit var percentageChange: BigDecimal
}