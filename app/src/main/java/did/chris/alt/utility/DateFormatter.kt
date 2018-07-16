package did.chris.alt.utility

import org.threeten.bp.format.DateTimeFormatter
import java.util.*

object DateFormatter {

    // Properties
    internal val DATE_TIME = makeDateTimeFormatter("HH:mm a dd/MM/yyyy")

    internal val DATE_HOUR_AMPM = makeDateTimeFormatter("dd-MM-YYYY ha")

    internal val DATE = makeDateTimeFormatter("dd/MM/yyyy")

    internal val TIME = makeDateTimeFormatter("HH:mm")

    // Private Functions
    private fun makeDateTimeFormatter(pattern: String): DateTimeFormatter {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    }
}
