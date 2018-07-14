package did.chris.alt.ui.interfaces

import did.chris.alt.data.coinListItem.ChangeData
import did.chris.alt.data.coinListItem.PriceData
import java.math.BigDecimal
import java.util.*

/**
 * SortableCoin
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
const val SORT_RANK = 0
const val SORT_RANK_REV = 1
const val SORT_NAME = 2
const val SORT_NAME_REV = 3
const val SORT_ONE_HOUR = 4
const val SORT_ONE_HOUR_REV = 5
const val SORT_TWENTY_FOUR_HOUR = 6
const val SORT_TWENTY_FOUR_HOUR_REV = 7
const val SORT_SEVEN_DAY = 8
const val SORT_SEVEN_DAY_REV = 9
const val SORT_VOLUME = 10
const val SORT_VOLUME_REV = 11

interface SortableCoin {
    var isFavourite: Boolean
    val name: String
    val rank: Int
    var sortedRank: Int
    val changeData: ChangeData
    val priceData: PriceData
    val volume24Hour: BigDecimal?
}

// Extension Functions

fun <T : SortableCoin> MutableList<T>.sortedByFavouritesThen(sortId: Int): MutableList<T> {
    val sorted = when (sortId) {
        SORT_RANK -> this.sortByRank()
        SORT_RANK_REV -> this.sortByRank().reversed()
        SORT_ONE_HOUR -> this.sortByOneHourChange()
        SORT_ONE_HOUR_REV -> this.sortByOneHourChange().reversed()
        SORT_TWENTY_FOUR_HOUR -> this.sortByTwentyFourHourChange()
        SORT_TWENTY_FOUR_HOUR_REV -> this.sortByTwentyFourHourChange().reversed()
        SORT_SEVEN_DAY -> this.sortBySevenDayChange()
        SORT_SEVEN_DAY_REV -> this.sortBySevenDayChange().reversed()
        SORT_VOLUME -> this.sortByVolume()
        SORT_VOLUME_REV -> this.sortByVolume().reversed()
        SORT_NAME -> this.sortByName()
        SORT_NAME_REV -> this.sortByName().reversed()
        else -> this.sortByRank()
    }
    this.forEachIndexed { index, item -> item.sortedRank = index + 1 }
    val splitLists = sorted.partition { it.isFavourite }
    val favourites = splitLists.first.sortByRank()
    return (favourites + splitLists.second).toMutableList()
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