package makes.flint.alt.factories

import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.tracker.TRANSACTION_BUY
import makes.flint.alt.data.tracker.TRANSACTION_SELL
import makes.flint.alt.data.tracker.TrackerDataEntry
import makes.flint.alt.data.tracker.TrackerDataTransaction
import makes.flint.alt.data.trackerListItem.*
import java.util.*
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
        return sortByValue(trackerListItems)
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
        return sortByDate(listTransactions)
    }

    private fun sortByDate(listTransactions: MutableList<TrackerTransaction>): MutableList<TrackerTransaction> {
        Collections.sort(listTransactions, Comparator { sortable1, sortable2 ->
            return@Comparator when {
                sortable1.pricePaid == sortable2.pricePaid -> 0
                sortable1.pricePaid < sortable2.pricePaid -> -1
                else -> 1
            }
        })
        return listTransactions
    }

    private fun sortByValue(trackerListItems: MutableList<TrackerListItem>): MutableList<TrackerListItem> {
        Collections.sort(trackerListItems, Comparator { sortable1, sortable2 ->
            return@Comparator when {
                sortable1.percentageChange == sortable2.percentageChange -> 0
                sortable1.percentageChange < sortable2.percentageChange -> -1
                else -> 1
            }
        })
        return trackerListItems
    }
}