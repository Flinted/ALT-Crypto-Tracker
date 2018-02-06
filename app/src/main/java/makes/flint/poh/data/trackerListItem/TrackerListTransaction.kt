package makes.flint.poh.data.trackerListItem

import makes.flint.poh.data.tracker.TrackerTransaction
import java.math.BigDecimal

/**
 * TrackerListTransaction
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class TrackerListTransaction(data: TrackerTransaction) {

    internal var dataId = data.id
    internal var transactionType = data.transactionType
    internal var purchaseDate = data.purchaseDate
    internal var loggedDate = data.loggedDate
    internal var quantity = BigDecimal(data.quantity)
    internal var pricePaid = if (!data.pricePaid.isNullOrBlank()) BigDecimal(data.pricePaid) else BigDecimal.ZERO
    internal var fees = if (!data.fees.isNullOrBlank()) BigDecimal(data.fees) else BigDecimal.ZERO
    internal var lastEditedDate = data.lastEditedDate
    internal var exchange = data.exchange
    internal var notes = data.notes

    internal fun transactionTotal(): BigDecimal {
        return quantity.multiply(pricePaid).add(fees)
    }
}