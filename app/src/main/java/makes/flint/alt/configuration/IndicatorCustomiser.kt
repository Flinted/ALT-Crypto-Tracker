package makes.flint.alt.configuration

import makes.flint.alt.R
import makes.flint.alt.data.coinListItem.*
import javax.inject.Inject

/**
 * IndicatorCustomiser
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
class IndicatorCustomiser @Inject constructor() {

    // Internal Functions

    internal fun getIcon(status: Int): Int {
        return when (status) {
            CHANGE_UP_EXTREME -> R.drawable.ic_up_extreme_24dp
            CHANGE_UP_MODERATE -> R.drawable.ic_up_moderate_24dp
            CHANGE_UP_SIGNIFICANT -> R.drawable.ic_up_significant_24dp
            CHANGE_DOWN_MODERATE -> R.drawable.ic_down_moderate_24dp
            CHANGE_DOWN_SIGNIFICANT -> R.drawable.ic_down_significant_24dp
            CHANGE_DOWN_EXTREME -> R.drawable.ic_down_extreme_24dp
            CHANGE_UNKNOWN -> R.drawable.ic_change_unknown_24dp
            else -> R.drawable.ic_movement_static_24dp
        }
    }

    internal fun getColor(status: Int): Int {
        return when (status) {
            CHANGE_DOWN_MODERATE, CHANGE_DOWN_SIGNIFICANT, CHANGE_DOWN_EXTREME, CHANGE_STATIC_NEGATIVE
            -> R.color.colorRed
            CHANGE_UNKNOWN -> R.color.colorOffBlack
            else -> R.color.colorGreen
        }
    }
}
