package makes.flint.alt.ui.market.extensions

import makes.flint.alt.R
import makes.flint.alt.ui.interfaces.*
import makes.flint.alt.ui.market.MarketFragment

/**
 * `MarketFragment+StringForSortId`
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */

fun MarketFragment.getStringIdFor(sortId: Int): Int {
    return when (sortId) {
        SORT_RANK -> R.string.sort_menu_market_cap
        SORT_RANK_REV -> R.string.sort_menu_market_cap_reversed
        SORT_NAME -> R.string.sort_menu_alphabetical
        SORT_NAME_REV -> R.string.sort_menu_alphabetical_reversed
        SORT_ONE_HOUR -> R.string.sort_menu_1H
        SORT_ONE_HOUR_REV -> R.string.sort_menu_1H_reversed
        SORT_TWENTY_FOUR_HOUR -> R.string.sort_menu_24H
        SORT_TWENTY_FOUR_HOUR_REV -> R.string.sort_menu_24H_reversed
        SORT_SEVEN_DAY -> R.string.sort_menu_7D
        SORT_SEVEN_DAY_REV -> R.string.sort_menu_7D_reversed
        SORT_VOLUME -> R.string.sort_menu_vol
        else -> R.string.sort_menu_vol_reversed
    }
}