package makes.flint.poh.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * CMCTimeStamp
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
private const val SYNC_WINDOW_MINS = 1L

open class CMCTimeStamp() : RealmObject() {

    @PrimaryKey
    internal var id = "CoinMarketCap"

    internal var stringTimeStampReference: String

    init {
        val timeNow = ZonedDateTime.now()
        this.stringTimeStampReference = timeNow.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    internal fun setLastSyncToNow() {
        val timeNow = ZonedDateTime.now()
        this.stringTimeStampReference = timeNow.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    internal fun shouldReSync(): Boolean {
        println("TIME IS $stringTimeStampReference")
        val timeNow = ZonedDateTime.now()
        val syncThreshold = ZonedDateTime.parse(stringTimeStampReference).plusMinutes(SYNC_WINDOW_MINS)
        val shouldSynchronize = timeNow.isAfter(syncThreshold)
        return shouldSynchronize
    }
}
