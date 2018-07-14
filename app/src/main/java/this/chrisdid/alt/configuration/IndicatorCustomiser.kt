package this.chrisdid.alt.configuration

import this.chrisdid.alt.data.coinListItem.*

/**
 * IndicatorCustomiser
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
class IndicatorCustomiser constructor(internal var icons: IconPack) {

    // Internal Functions
    internal fun getIcon(status: Int): Int {
        return when (status) {
            CHANGE_UP_EXTREME       -> icons.upExtreme
            CHANGE_UP_MODERATE      -> icons.upModerate
            CHANGE_UP_SIGNIFICANT   -> icons.upSignificant
            CHANGE_DOWN_MODERATE    -> icons.downModerate
            CHANGE_DOWN_SIGNIFICANT -> icons.downSignificant
            CHANGE_DOWN_EXTREME     -> icons.downExtreme
            CHANGE_UNKNOWN          -> icons.unknown
            else                    -> icons.neutral
        }
    }

    fun getColor(state: Int) = icons.getColor(state)

    fun updateIconPack(iconPack: IconPack) {
        this.icons = iconPack
    }
}
