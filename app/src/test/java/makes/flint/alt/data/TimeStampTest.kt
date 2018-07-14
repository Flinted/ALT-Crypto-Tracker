package did.chris.alt.data

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

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