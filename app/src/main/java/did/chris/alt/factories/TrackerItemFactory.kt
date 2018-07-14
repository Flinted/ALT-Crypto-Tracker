package did.chris.alt.factories

import io.realm.RealmList
import did.chris.alt.data.coinListItem.CoinListItem
import did.chris.alt.data.tracker.TRANSACTION_BUY
import did.chris.alt.data.tracker.TRANSACTION_SELL
import did.chris.alt.data.tracker.TrackerDataEntry
import did.chris.alt.data.tracker.TrackerDataTransaction
import did.chris.alt.data.trackerListItem.*
import java.util.*
import javax.inject.Inject

/**
 * TrackerItemFactory
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerItemFactory @Inject constructor() {

    // Internal Functions

    internal fun makeTrackerItems(data: List<TrackerDataEntry>,
                                  coinListItems: List<CoinListItem>): MutableList<TrackerListItem> {
        val trackerListItems: MutableList<TrackerListItem> = mutableListOf()
        if (data.isEmpty()) {
            return trackerListItems
        }
        data.forEach {
            val coin = findCoinFor(it.name, coinListItems) ?: return@forEach
            val listTransactions: MutableList<TrackerTransaction> = makeListTransactions(it.transactions)
            val item = TrackerListItem(it.name, it.symbol, it.id, coin, listTransactions)
            item.transactions = listTransactions
            trackerListItems.add(item)
        }
        return sortByValue(trackerListItems)
    }

    // Private Functions

    private fun findCoinFor(name: String, coinListItems: List<CoinListItem>): CoinListItem? {
        return coinListItems.find {
            it.name == name
        }
    }

    private fun makeListTransactions(transactions: RealmList<TrackerDataTransaction>): MutableList<TrackerTransaction> {
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
                sortable1.currentValueUSD == sortable2.currentValueUSD -> 0
                sortable1.currentValueUSD > sortable2.currentValueUSD -> -1
                else -> 1
            }
        })
        return trackerListItems
    }
}
