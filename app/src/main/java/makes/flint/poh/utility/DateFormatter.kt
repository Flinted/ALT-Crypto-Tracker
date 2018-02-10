package makes.flint.poh.utility

import org.threeten.bp.format.DateTimeFormatter
import java.util.*

/**
 * DateFormatter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */

object DateFormatter {

    // Internal properties

    internal val DATE_TIME = makeDateTimeFormatter("HH:mm a dd/MM/yyyy")

    internal val DATE = makeDateTimeFormatter("dd/MM/yyyy")

    // Private Functions

    private fun makeDateTimeFormatter(pattern: String): DateTimeFormatter {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    }
}
