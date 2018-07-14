package this.chrisdid.alt.data.coinListItem

import this.chrisdid.alt.data.response.CoinResponse
import this.chrisdid.alt.ui.interfaces.SortableCoin
import this.chrisdid.alt.utility.NumberFormatter
import java.math.BigDecimal

/**
 * CoinListItem
 * Copyright © 2018 ChrisDidThis. All rights reserved.
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
        override val priceData: PriceData,
        override val changeData: ChangeData,
        override var isFavourite: Boolean
) : SortableCoin, MarketListItem {

    // Properties

    override val name = coinResponse.name
    internal val id = coinResponse.id
    internal val symbol = coinResponse.symbol
    override val rank = coinResponse.provideRank()
    override var sortedRank = 0
    override val volume24Hour = coinResponse.provideVolume24Hour()
    internal val availableSupply = coinResponse.provideAvailableSupply()
    internal var totalSupply = coinResponse.provideTotalSupply()
    internal var symbolFormatted = "($symbol)"
    internal var searchKey = "$name ($symbol)"
    internal val availableSupplyFormatted = availableSupplyFormatted()
    internal val totalSupplyFormatted = totalSupplyFormatted()
    internal val volume24HourFormatted = volume24HourFormatted()

    // Internal Functions

    internal fun marketCapFormatted(): String {
        return priceData.marketCapFormatted
    }

    // Private Functions

    private fun volume24HourFormatted(): String {
        volume24Hour?.let {
            return NumberFormatter.formatCurrency(it, 0, 0)
        }
        return "Unknown"
    }

    private fun availableSupplyFormatted(): String {
        availableSupply?.let {
            val number = BigDecimal(it)
            return NumberFormatter.format(number, 0)
        }
        return "Unknown"
    }

    private fun totalSupplyFormatted(): String {
        totalSupply?.let {
            val number = BigDecimal(it)
            return NumberFormatter.format(number, 0)
        }
        return "Unknown"
    }
}

