package makes.flint.poh.factories

import makes.flint.poh.data.coinListItem.ChangeData
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.coinListItem.PriceData
import makes.flint.poh.data.coinListItem.compareRank
import makes.flint.poh.data.response.CoinResponse
import java.util.*

/**
 * CoinListItemFactory
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinListItemFactory {

    // Internal Functions
    internal fun makeCoinListItems(inputItems: MutableList<CoinResponse>): MutableList<CoinListItem> {
        val coinListItems = inputItems.map { makeCoinListItem(it) } as MutableList<CoinListItem>
        val sortedCoinListItems = sortCoins(coinListItems)
        return sortedCoinListItems
    }

    // Private Functions
    private fun makeCoinListItem(inputItem: CoinResponse): CoinListItem {
        val priceData = PriceData(inputItem)
        val changeData = ChangeData(inputItem)
        val coinListItem = CoinListItem(inputItem, priceData, changeData)
        return coinListItem
    }

    private fun sortCoins(coinList: MutableList<CoinListItem>): MutableList<CoinListItem> {
        Collections.sort(coinList, Comparator { coin1, coin2 ->
            return@Comparator coin1.compareRank(coin2)
        })
        return coinList
    }
}
