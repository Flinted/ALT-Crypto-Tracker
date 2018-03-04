package makes.flint.alt.data

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * TimeStampTest
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class TimeStampTest {
    private lateinit var timeStamp: TimeStamp

    @Before
    fun setUp() {
        timeStamp = TimeStamp()
    }

    @Test
    fun testInstantiation() {
        assertNotNull(timeStamp.id)
        assertEquals(false, timeStamp.shouldReSync())
    }

    @Test
    fun testInstantiationByDate() {
        timeStamp = TimeStamp("12/01/2018")
        assertNotNull(timeStamp.id)
        assertEquals(true, timeStamp.shouldReSync())
    }
}