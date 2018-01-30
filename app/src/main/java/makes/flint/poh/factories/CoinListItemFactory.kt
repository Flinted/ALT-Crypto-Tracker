package makes.flint.poh.factories

import makes.flint.poh.data.coinListItem.ChangeData
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.coinListItem.PriceData
import makes.flint.poh.data.coinListItem.compareRank
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import makes.flint.poh.data.response.CoinResponse
import java.util.*
import kotlin.collections.HashMap

/**
 * CoinListItemFactory
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinListItemFactory {

    // Internal Functions
    internal fun makeCoinListItems(inputItems: MutableList<CoinResponse>,
                                   favouriteCoins: MutableList<FavouriteCoin>?
    ): MutableList<CoinListItem> {
        val coinListMap = makeFavouritesHashMap(favouriteCoins)
        val coinListItems = inputItems.map { makeCoinListItem(it, coinListMap) } as MutableList<CoinListItem>
        return sortCoins(coinListItems)
    }

    private fun makeFavouritesHashMap(favouriteCoins: MutableList<FavouriteCoin>?): HashMap<String, FavouriteCoin> {
        favouriteCoins ?: return hashMapOf()
        val favouritesMap = HashMap<String, FavouriteCoin>()
        favouriteCoins.forEach {
            favouritesMap[it.symbol] = it
        }
        return favouritesMap
    }

    // Private Functions
    private fun makeCoinListItem(inputItem: CoinResponse,
                                 favouriteCoins: HashMap<String, FavouriteCoin>
    ): CoinListItem {
        val isFavourite = favouriteCoins[inputItem.symbol] != null
        val priceData = PriceData(inputItem)
        val changeData = ChangeData(inputItem)
        return CoinListItem(inputItem, priceData, changeData, isFavourite)
    }

    private fun sortCoins(coinList: MutableList<CoinListItem>): MutableList<CoinListItem> {
        val splitLists = coinList.partition { it.isFavourite }
        val favourites = sortByRank(splitLists.first)
        val otherCoins = sortByRank(splitLists.second)
        return (favourites + otherCoins).toMutableList()
    }

    private fun sortByRank(coinList: List<CoinListItem>): List<CoinListItem> {
        Collections.sort(coinList, Comparator { coin1, coin2 ->
            return@Comparator coin1.compareRank(coin2)
        })
        return coinList
    }
}
