package makes.flint.alt.ui.interfaces

import makes.flint.alt.data.coinListItem.ChangeData
import makes.flint.alt.data.coinListItem.PriceData
import java.math.BigDecimal
import java.util.*

/**
 * SortableCoin
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
const val SORT_ONE_HOUR = 0
const val SORT_ONE_HOUR_REV = 1
const val SORT_TWENTY_FOUR_HOUR = 2
const val SORT_TWENTY_FOUR_HOUR_REV = 3
const val SORT_SEVEN_DAY = 4
const val SORT_SEVEN_DAY_REV = 5
const val SORT_VOLUME = 6
const val SORT_VOLUME_REV = 7
const val SORT_NAME = 8
const val SORT_NAME_REV = 9
const val SORT_RANK = 10
const val SORT_RANK_REV = 11

interface SortableCoin {
    var isFavourite: Boolean
    val name: String
    val rank: Int
    val changeData: ChangeData
    val priceData: PriceData
    val volume24Hour: BigDecimal?
}

// Extension Functions

fun <T : SortableCoin> MutableList<T>.sortedByFavouritesThen(sortId: Int): MutableList<T> {
    val splitLists = this.partition { it.isFavourite }
    val favourites = splitLists.first.sortByRank()
    val otherSortables = when (sortId) {
        SORT_RANK -> splitLists.second.sortByRank()
        SORT_RANK_REV -> splitLists.second.sortByRank().reversed()
        SORT_ONE_HOUR -> splitLists.second.sortByOneHourChange()
        SORT_ONE_HOUR_REV -> splitLists.second.sortByOneHourChange().reversed()
        SORT_TWENTY_FOUR_HOUR -> splitLists.second.sortByTwentyFourHourChange()
        SORT_TWENTY_FOUR_HOUR_REV -> splitLists.second.sortByTwentyFourHourChange().reversed()
        SORT_SEVEN_DAY -> splitLists.second.sortBySevenDayChange()
        SORT_SEVEN_DAY_REV -> splitLists.second.sortBySevenDayChange().reversed()
        SORT_VOLUME -> splitLists.second.sortByVolume()
        SORT_VOLUME_REV -> splitLists.second.sortByVolume().reversed()
        SORT_NAME -> splitLists.second.sortByName()
        SORT_NAME_REV -> splitLists.second.sortByName().reversed()
        else -> splitLists.second.sortByRank()
    }
    return (favourites + otherSortables).toMutableList()
}

private fun SortableCoin.compareRank(comparator: SortableCoin): Int {
    return when {
        this.rank == comparator.rank -> 0
        this.rank < comparator.rank -> -1
        else -> 1
    }
}

private fun SortableCoin.compare1Hour(comparator: SortableCoin): Int {
    val float1 = this.changeData.percentChange1H?.toFloat() ?: 0f
    val float2 = comparator.changeData.percentChange1H?.toFloat() ?: 0f
    return compareFloats(float1, float2)
}

private fun SortableCoin.compare24Hour(comparator: SortableCoin): Int {
    val float1 = this.changeData.percentChange24H?.toFloat() ?: 0f
    val float2 = comparator.changeData.percentChange24H?.toFloat() ?: 0f
    return compareFloats(float1, float2)
}

private fun SortableCoin.compare7Day(comparator: SortableCoin): Int {
    val float1 = this.changeData.percentChange7D?.toFloat() ?: 0f
    val float2 = comparator.changeData.percentChange7D?.toFloat() ?: 0f
    return compareFloats(float1, float2)
}

private fun SortableCoin.compareName(comparator: SortableCoin): Int {
    val name1 = this.name
    val name2 = comparator.name
    return when {
        name1 == name2 -> 0
        name1 < name2 -> -1
        else -> 1
    }
}

private fun SortableCoin.compareVolume(comparator: SortableCoin): Int {
    val volume1 = this.volume24Hour?.toFloat() ?: 0f
    val volume2 = comparator.volume24Hour?.toFloat() ?: 0f
    return compareFloats(volume1, volume2)
}

private fun compareFloats(float1: Float, float2: Float): Int {
    return when {
        float1 == float2 -> 0
        float1 > float2 -> -1
        else -> 1
    }
}

private fun <T : SortableCoin> List<T>.sortByRank(): List<T> {
    Collections.sort(this, Comparator { sortable1, sortable2 ->
        return@Comparator sortable1.compareRank(sortable2)
    })
    return this
}

private fun <T : SortableCoin> List<T>.sortByOneHourChange(): List<T> {
    Collections.sort(this, Comparator { sortable1, sortable2 ->
        return@Comparator sortable1.compare1Hour(sortable2)
    })
    return this
}

private fun <T : SortableCoin> List<T>.sortByTwentyFourHourChange(): List<T> {
    Collections.sort(this, Comparator { sortable1, sortable2 ->
        return@Comparator sortable1.compare24Hour(sortable2)
    })
    return this
}

private fun <T : SortableCoin> List<T>.sortBySevenDayChange(): List<T> {
    Collections.sort(this, Comparator { sortable1, sortable2 ->
        return@Comparator sortable1.compare7Day(sortable2)
    })
    return this
}

private fun <T : SortableCoin> List<T>.sortByName(): List<T> {
    Collections.sort(this, Comparator { sortable1, sortable2 ->
        return@Comparator sortable1.compareName(sortable2)
    })
    return this
}

private fun <T : SortableCoin> List<T>.sortByVolume(): List<T> {
    Collections.sort(this, Comparator { sortable1, sortable2 ->
        return@Comparator sortable1.compareVolume(sortable2)
    })
    return this
}