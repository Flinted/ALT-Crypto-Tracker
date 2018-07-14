package did.chris.alt.utility

import org.threeten.bp.format.DateTimeFormatter
import java.util.*

/**
 * DateFormatter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

object DateFormatter {

    // Internal properties

    internal val DATE_TIME = makeDateTimeFormatter("HH:mm a dd/MM/yyyy")

    internal val DATE = makeDateTimeFormatter("dd/MM/yyyy")

    internal val TIME = makeDateTimeFormatter("HH:mm")

    // Private Functions

    private fun makeDateTimeFormatter(pattern: String): DateTimeFormatter {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    }
}
