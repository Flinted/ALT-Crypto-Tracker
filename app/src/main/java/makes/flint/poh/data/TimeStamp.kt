package makes.flint.poh.data

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * TimeStamp
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
private const val SYNC_WINDOW_MINS = 1L

open class TimeStamp() {

    internal var timeStampISO8601: String

    init {
        val timeNow = ZonedDateTime.now()
        this.timeStampISO8601 = timeNow.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    internal fun setLastSyncToNow() {
        val timeNow = ZonedDateTime.now()
        this.timeStampISO8601 = timeNow.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    internal fun shouldReSync(): Boolean {
        val timeNow = ZonedDateTime.now()
        val syncThreshold = ZonedDateTime.parse(timeStampISO8601).plusMinutes(SYNC_WINDOW_MINS)
        val shouldSynchronize = timeNow.isAfter(syncThreshold)
        return shouldSynchronize
    }
}
