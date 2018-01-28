package makes.flint.poh.data.coinListItem

import makes.flint.poh.data.response.CoinResponse

/**
 * CoinListItem
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
const val CHANGE_UP_EXTREME = 0
const val CHANGE_UP_SIGNIFICANT = 1
const val CHANGE_UP_MODERATE = 2
const val CHANGE_STATIC_POSITIVE = 3
const val CHANGE_STATIC_NEGATIVE = 4
const val CHANGE_DOWN_MODERATE = 5
const val CHANGE_DOWN_SIGNIFICANT = 6
const val CHANGE_DOWN_EXTREME = 7
const val CHANGE_UNKNOWN = 8

class CoinListItem(
        coinResponse: CoinResponse,
        internal val priceData: PriceData,
        internal val changeData: ChangeData
) {
    internal val name = coinResponse.name
    internal val id = coinResponse.id
    internal val symbol = coinResponse.symbol
    internal val rank = coinResponse.provideRank()
    internal val volume24Hour = coinResponse.provideVolume24Hour()
    internal var availableSupply = coinResponse.provideAvailableSupply()
    internal var totalSupply = coinResponse.provideTotalSupply()
}

internal fun CoinListItem.compareRank(comparator: CoinListItem): Int {
    if (this.rank == comparator.rank) {
        return 0
    }
    if (this.rank < comparator.rank) {
        return -1
    }
    return 1
}