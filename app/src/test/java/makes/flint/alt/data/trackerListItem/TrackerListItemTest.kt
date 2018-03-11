package makes.flint.alt.data.trackerListItem

import junit.framework.Assert.assertEquals
import makes.flint.alt.data.TimeStamp
import makes.flint.alt.data.tracker.TrackerDataTransaction
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

/**
 * TrackerListItemTest
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerListItemTest {

    private lateinit var trackerListItemNoCoin: TrackerListItem

    @Before
    fun setUp() {
        val name = "TestName"
        val symbol = "TestSymbol"
        val id = "TestId"
        val trackerTransactions = makeTrackerTransactions()
        trackerListItemNoCoin = TrackerListItem(name, symbol, id, null, trackerTransactions)
    }

    @Test
    fun testNumberOwned() {
        val expectedResult = BigDecimal("650.125")
        assertEquals(expectedResult, trackerListItemNoCoin.numberOwned)
    }

    @Test
    fun testNumberOwnedFormatted() {
        val expectedResult = "650.125"
        assertEquals(expectedResult, trackerListItemNoCoin.numberOwnedFormatted)
    }

    // Private Functions

    private fun makeTrackerTransactions(): MutableList<TrackerTransaction> {
        val timeStamp = TimeStamp()
        val transactionData1 = TrackerDataTransaction()
        transactionData1.exchange = "testExchange1"
        transactionData1.fees = "20.00"
        transactionData1.id = "testId1"
        transactionData1.purchaseDate = timeStamp
        transactionData1.loggedDate = timeStamp
        transactionData1.notes = "testNotes1"
        transactionData1.quantity = "100.123"
        transactionData1.pricePaid = "1.02"
        val transaction1 = TrackerTransactionBuy(transactionData1)

        val transactionData2 = TrackerDataTransaction()
        transactionData2.exchange = "testExchange2"
        transactionData2.fees = "10.00"
        transactionData2.id = "testId2"
        transactionData2.purchaseDate = timeStamp
        transactionData2.loggedDate = timeStamp
        transactionData2.notes = "testNotes2"
        transactionData2.quantity = "50.002"
        transactionData2.pricePaid = "1.35"
        val transaction2 = TrackerTransactionBuy(transactionData2)

        val transactionData3 = TrackerDataTransaction()
        transactionData3.exchange = "testExchange3"
        transactionData3.id = "testId3"
        transactionData3.notes = "testNotes3"
        transactionData3.purchaseDate = timeStamp
        transactionData3.loggedDate = timeStamp
        transactionData3.quantity = "500"
        transactionData3.pricePaid = "0.95"
        val transaction3 = TrackerTransactionBuy(transactionData3)

        return mutableListOf(transaction1, transaction2, transaction3)
    }
}
