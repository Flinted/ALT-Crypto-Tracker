package this.chrisdid.alt.data

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import this.chrisdid.alt.data.trackerListItem.TrackerListItem
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

/**
 * SummaryTest
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class SummaryTest {

    private lateinit var summary: Summary

    @Before
    fun setUp() {
        val initialValue = BigDecimal("1000.00")
        val amountSpent = BigDecimal("500.00")
        val amountSold = BigDecimal("-300.00")
        val currentFiat = BigDecimal("100.00")
        val currentBTC = BigDecimal("0.0044")
        val percentageChange = BigDecimal("0.75")
        val data = listOf<TrackerListItem>()
        summary = Summary(
                initialValue,
                amountSpent,
                amountSold,
                currentFiat,
                currentBTC,
                percentageChange,
                data)
    }

    @Test
    fun testInitialValueFormatted() {
        val expectedResult = "$1,000.00"
        assertEquals(expectedResult, summary.initialValueFormatted())
    }

    @Test
    fun testAmountSpentFormatted() {
        val expectedResult = "$500.00"
        assertEquals(expectedResult, summary.amountSpentFormatted())
    }

    @Test
    fun testAmountSoldFormatted() {
        val expectedResult = "($300.00)"
        assertEquals(expectedResult, summary.amountSoldFormatted())
    }

    @Test
    fun testCurrentStandingFormatted() {
        val expectedResult = "$200.00"
        assertEquals(expectedResult, summary.currentStandingFormatted())
    }

    @Test
    fun testCurrentValueFiatFormatted() {
        val expectedResult = "$100.00"
        assertEquals(expectedResult, summary.currentValueFiatFormatted())
    }

    @Test
    fun testCurrentValueBTCFormatted() {
        val expectedResult = "B0.0044"
        assertEquals(expectedResult, summary.currentValueBTCFormatted())
    }

    @Test
    fun testPercentageChangeFormatted() {
        val expectedResult = "75.00%"
        assertEquals(expectedResult, summary.percentageChangeFormatted())
    }

    @Test
    fun testDataEntryListPresent() {
        val expectedResult = 0
        val trackerEntries = summary.getTrackerEntries()
        assertNotNull(trackerEntries)
        assertEquals(expectedResult, trackerEntries.count())
    }
}
