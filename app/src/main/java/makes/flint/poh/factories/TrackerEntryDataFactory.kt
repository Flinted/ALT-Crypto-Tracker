package makes.flint.poh.factories

import makes.flint.poh.data.TimeStamp
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.tracker.TRANSACTION_MINED
import makes.flint.poh.data.tracker.TRANSACTION_SELL
import makes.flint.poh.data.tracker.TrackerEntryData
import makes.flint.poh.data.tracker.TrackerTransaction
import javax.inject.Inject

/**
 * TrackerEntryDataFactory
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerEntryDataFactory @Inject constructor() {

    fun makeTrackerEntryDataFor(trackerEntryData: TrackerEntryData?,
                                coin: CoinListItem?,
                                exchange: String?,
                                quantity: String?,
                                price: String?,
                                fees: String?,
                                date: String,
                                notes: String?,
                                type: String
    ): TrackerEntryData? {
        coin ?: return null
        val quantityInput = !quantity.isNullOrBlank()
        val priceInput = !price.isNullOrBlank() || type == TRANSACTION_MINED
        if (!quantityInput || !priceInput) {
            return null
        }
        val trackerEntry = trackerEntryData ?: TrackerEntryData(coin.symbol, coin.name)
        val transaction = TrackerTransaction()
        transaction.exchange = exchange
        transaction.quantity = getQuantityForType(type, quantity)
        transaction.pricePaid = price
        transaction.fees = fees
        transaction.purchaseDate = TimeStamp(date)
        transaction.loggedDate = TimeStamp()
        transaction.notes = notes
        transaction.transactionType = type
        trackerEntry.transactions.add(transaction)
        return trackerEntry
    }

    private fun getQuantityForType(type: String, quantity: String?): String {
        quantity ?: return "0"
        if (type == TRANSACTION_SELL) {
            return "-$quantity"
        }
        return quantity
    }
}
