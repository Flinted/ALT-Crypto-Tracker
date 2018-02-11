package makes.flint.poh.factories

import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.tracker.TRANSACTION_BUY
import makes.flint.poh.data.tracker.TRANSACTION_SELL
import makes.flint.poh.data.tracker.TrackerDataEntry
import makes.flint.poh.data.tracker.TrackerDataTransaction
import makes.flint.poh.data.trackerListItem.*
import javax.inject.Inject

/**
 * TrackerItemFactory
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerItemFactory @Inject constructor() {

    fun makeTrackerItems(data: List<TrackerDataEntry>, coinListItems: List<CoinListItem>): MutableList<TrackerListItem> {
        val trackerListItems: MutableList<TrackerListItem> = mutableListOf()
        if (data.isEmpty()) {
            return trackerListItems
        }
        data.forEach {
            val coin = findCoinFor(it.name, coinListItems)
            val listTransactions: MutableList<TrackerTransaction> = makeListTransactions(it.transactions)
            val item = TrackerListItem(it.name, it.symbol, it.id, coin, listTransactions)
            item.transactions = listTransactions
            trackerListItems.add(item)
        }
        return trackerListItems
    }

    private fun findCoinFor(name: String, coinListItems: List<CoinListItem>): CoinListItem? {
        return coinListItems.find {
            it.name == name
        }
    }

    private fun makeListTransactions(transactions: List<TrackerDataTransaction>): MutableList<TrackerTransaction> {
        if (transactions.isEmpty()) {
            return mutableListOf()
        }
        val listTransactions: MutableList<TrackerTransaction> = mutableListOf()
        transactions.forEach {
            val transaction = when (it.transactionType) {
                TRANSACTION_SELL -> TrackerTransactionSale(it)
                TRANSACTION_BUY -> TrackerTransactionBuy(it)
                else -> TrackerTransactionEarned(it)
            }
            listTransactions.add(transaction)
        }
        return listTransactions
    }
}
