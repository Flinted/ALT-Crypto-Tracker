package makes.flint.poh.data.trackerListItem

import makes.flint.poh.data.TimeStamp
import makes.flint.poh.utility.NumberFormatter
import java.math.BigDecimal

/**
 * TrackerTransaction
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface TrackerTransaction {
    var dataId: String
    var purchaseDate: TimeStamp
    var loggedDate: TimeStamp
    var lastEditedDate: TimeStamp?
    var quantity: BigDecimal
    var pricePaid: BigDecimal
    var fees: BigDecimal
    var totalTransactionValue: BigDecimal
    var exchange: String?
    var notes: String?

    fun transactionTotal(): BigDecimal {
        return quantity.multiply(pricePaid).add(fees)
    }

    fun transactionTotalFormatted(): String {
        return NumberFormatter.formatCurrency(totalTransactionValue, 2)
    }

    fun pricePaidFormatted(): String {
        return NumberFormatter.formatCurrency(pricePaid, 5)
    }
}
