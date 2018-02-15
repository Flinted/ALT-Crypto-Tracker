package makes.flint.alt.data.trackerListItem

import makes.flint.alt.data.tracker.TrackerDataTransaction
import java.math.BigDecimal

/**
 * TrackerTransactionEarned
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class TrackerTransactionEarned(data: TrackerDataTransaction) : TrackerTransaction {

    override var dataId = data.id
    override var purchaseDate = data.purchaseDate
    override var loggedDate = data.loggedDate
    override var lastEditedDate = data.lastEditedDate
    override var quantity = BigDecimal(data.quantity)
    override var pricePaid = BigDecimal.ZERO
    override var fees = if (!data.fees.isNullOrBlank()) BigDecimal(data.fees) else BigDecimal.ZERO
    override var totalTransactionValue = transactionTotal()
    override var exchange: String? = null
    override var notes = data.notes
}