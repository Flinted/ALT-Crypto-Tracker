package did.chris.alt.data

import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.data.services.interfaces.RealmDeletable
import did.chris.alt.utility.DateFormatter
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

open class TimeStamp() : RealmObject(), RealmDeletable {

    // Properties
    @PrimaryKey
    var id = UUID.randomUUID().toString()
    internal var timeStampISO8601: String
    private var invalidated = false

    // Lifecycle
    constructor(date: String) : this() {
        val localDate = LocalDate.parse(date, DateFormatter.DATE)
        val time = localDate.atStartOfDay(ZoneOffset.UTC)
        this.timeStampISO8601 = time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    init {
        val timeNow = ZonedDateTime.now()
        this.timeStampISO8601 = timeNow.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    // Overrides

    override fun nestedDeleteFromRealm() {
        this.deleteFromRealm()
    }

    // Internal Functions
    internal fun shouldReSync(): Boolean {
        val timeNow = ZonedDateTime.now()
        val syncThreshold =
            ZonedDateTime.parse(timeStampISO8601).plusMinutes(ALTSharedPreferences.getRefreshGap())
        return timeNow.isAfter(syncThreshold) || invalidated
    }

    internal fun invalidate() {
        invalidated = true
    }
}
