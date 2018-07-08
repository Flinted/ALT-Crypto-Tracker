package makes.flint.alt.configuration

import makes.flint.alt.data.coinListItem.*
import javax.inject.Inject

/**
 * IndicatorCustomiser
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
class IndicatorCustomiser @Inject constructor() {

    internal var icons: IconPack = DefaultIconPack()

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

    fun updateIcons(iconPack: IconPack) {
        this.icons = iconPack
    }
}
