package makes.flint.alt.ui.main.extensions

import makes.flint.alt.R
import makes.flint.alt.ui.interfaces.*
import makes.flint.alt.ui.main.MainActivity

/**
 * `MainActivity+SortMaps`
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */

fun MainActivity.idToSortMap(): HashMap<Int, Int> {
    return hashMapOf(
            R.id.sort_rank_ascending to SORT_RANK,
            R.id.sort_rank_descending to SORT_RANK_REV,
            R.id.sort_alphabetical_ascending to SORT_NAME,
            R.id.sort_alphabetical_descending to SORT_RANK_REV,
            R.id.sort_1H_ascending to SORT_ONE_HOUR,
            R.id.sort_1H_descending to SORT_ONE_HOUR_REV,
            R.id.sort_24H_ascending to SORT_TWENTY_FOUR_HOUR,
            R.id.sort_24H_descending to SORT_TWENTY_FOUR_HOUR_REV,
            R.id.sort_7D_ascending to SORT_SEVEN_DAY,
            R.id.sort_7D_descending to SORT_SEVEN_DAY_REV,
            R.id.sort_volume_ascending to SORT_VOLUME,
            R.id.sort_volume_descending to SORT_VOLUME_REV
    )
}

fun MainActivity.sortToIdMap(): HashMap<Int, Int> {
    return hashMapOf(
            SORT_RANK to R.id.sort_rank_ascending,
            SORT_RANK_REV to R.id.sort_rank_descending,
            SORT_NAME to R.id.sort_alphabetical_ascending,
            SORT_NAME_REV to R.id.sort_alphabetical_descending,
            SORT_VOLUME to R.id.sort_volume_ascending,
            SORT_VOLUME_REV to R.id.sort_volume_descending,
            SORT_TWENTY_FOUR_HOUR to R.id.sort_24H_ascending,
            SORT_TWENTY_FOUR_HOUR_REV to R.id.sort_24H_descending,
            SORT_ONE_HOUR to R.id.sort_1H_ascending,
            SORT_ONE_HOUR_REV to R.id.sort_1H_descending,
            SORT_SEVEN_DAY to R.id.sort_7D_ascending,
            SORT_SEVEN_DAY_REV to R.id.sort_1H_descending
    )
}