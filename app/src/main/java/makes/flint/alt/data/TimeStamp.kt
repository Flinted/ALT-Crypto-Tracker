package makes.flint.alt.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.services.interfaces.RealmDeletable
import makes.flint.alt.utility.DateFormatter
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

/**
 * TimeStamp
 * Copyright © 2018  Flint Makes. All rights reserved.
 */

open class TimeStamp() : RealmObject(), RealmDeletable {

    @PrimaryKey
    internal var id = UUID.randomUUID().toString()

    internal var timeStampISO8601: String

    constructor(date: String) : this() {
        val localDate = LocalDate.parse(date, DateFormatter.DATE)
        val time = localDate.atStartOfDay(ZoneOffset.UTC)
        this.timeStampISO8601 = time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    init {
        val timeNow = ZonedDateTime.now()
        this.timeStampISO8601 = timeNow.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    internal fun shouldReSync(): Boolean {
        val timeNow = ZonedDateTime.now()
        val syncThreshold = ZonedDateTime.parse(timeStampISO8601).plusMinutes(POHSettings.refreshGap)
        return timeNow.isAfter(syncThreshold)
    }

    override fun nestedDeleteFromRealm() {
        this.deleteFromRealm()
    }
}
