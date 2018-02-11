package makes.flint.poh.data.trackerListItem

import makes.flint.poh.data.tracker.TrackerDataTransaction
import java.math.BigDecimal

/**
 * TrackerTransactionBuy
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class TrackerTransactionBuy(data: TrackerDataTransaction) : TrackerTransaction {

    override var dataId = data.id
    override var purchaseDate = data.purchaseDate
    override var loggedDate = data.loggedDate
    override var lastEditedDate = data.lastEditedDate
    override var quantity = BigDecimal(data.quantity)
    override var pricePaid = if (!data.pricePaid.isNullOrBlank()) BigDecimal(data.pricePaid) else BigDecimal.ZERO
    override var fees = if (!data.fees.isNullOrBlank()) BigDecimal(data.fees) else BigDecimal.ZERO
    override var totalTransactionValue = transactionTotal()
    override var exchange = data.exchange
    override var notes = data.notes
}
