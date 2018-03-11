package makes.flint.alt.data.trackerListItem

import makes.flint.alt.data.TimeStamp
import makes.flint.alt.utility.NumberFormatter
import java.math.BigDecimal

/**
 * TrackerTransaction
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface TrackerTransaction {

    // Properties
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

    // Functions
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
