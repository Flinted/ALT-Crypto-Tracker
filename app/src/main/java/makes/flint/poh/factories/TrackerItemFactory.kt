package makes.flint.poh.factories

import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.tracker.TrackerEntryData
import makes.flint.poh.data.tracker.TrackerTransaction
import makes.flint.poh.data.trackerListItem.TrackerListItem
import makes.flint.poh.data.trackerListItem.TrackerListTransaction
import javax.inject.Inject

/**
 * TrackerItemFactory
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class TrackerItemFactory @Inject constructor() {

    fun makeTrackerItems(data: List<TrackerEntryData>, coinListItems: List<CoinListItem>): MutableList<TrackerListItem> {
        val trackerListItems: MutableList<TrackerListItem> = mutableListOf()
        if (data.isEmpty()) {
            return trackerListItems
        }
        data.forEach {
            val item = TrackerListItem(it.name, it.symbol, it.id)
            val coin = findCoinFor(it.name, coinListItems)
            item.associatedCoin = coin
            val listTransactions: MutableList<TrackerListTransaction> = makeListTransactions(it.transactions)
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

    private fun makeListTransactions(transactions: List<TrackerTransaction>): MutableList<TrackerListTransaction> {
        if (transactions.isEmpty()) {
            return mutableListOf()
        }
        val listTransactions: MutableList<TrackerListTransaction> = mutableListOf()
        transactions.forEach {
            val transaction = TrackerListTransaction(it)
            listTransactions.add(transaction)
        }
        return listTransactions
    }
}
