package makes.flint.alt.factories

import makes.flint.alt.configuration.ALTSharedPreferences
import makes.flint.alt.data.coinListItem.ChangeData
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.coinListItem.PriceData
import makes.flint.alt.data.coinListItem.marketData.MarketData
import makes.flint.alt.data.favouriteCoins.FavouriteCoin
import makes.flint.alt.data.response.CoinResponse
import makes.flint.alt.ui.interfaces.sortedByFavouritesThen
import javax.inject.Inject

/**
 * CoinListItemFactory
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class CoinListItemFactory @Inject constructor() {

    // Properties
    private var count = 1
    private var twentyFourHourMarket = 0f
    private var sevenDayMarket = 0f
    private var itemsInAverage = 0

    // Internal Functions

    internal fun makeCoinListItems(
        inputItems: MutableList<CoinResponse>,
        favouriteCoins: MutableList<FavouriteCoin>?
    ): MutableList<CoinListItem> {
        twentyFourHourMarket = 0f
        sevenDayMarket = 0f
        val coinListMap = makeFavouritesHashMap(favouriteCoins)
        val coinListItems =
            inputItems.map { makeCoinListItem(it, coinListMap) } as MutableList<CoinListItem>
        itemsInAverage = coinListItems.size
        return coinListItems.sortedByFavouritesThen(ALTSharedPreferences.getSort())
    }

    internal fun updateFavouriteCoins(
        cachedCoins: List<CoinListItem>?,
        favouriteCoins: MutableList<FavouriteCoin>
    ): MutableList<CoinListItem>? {
        val mutableCachedCoins = cachedCoins?.toMutableList() ?: return mutableListOf()
        val favouritesMap = makeFavouritesHashMap(favouriteCoins)
        cachedCoins.forEach {
            it.isFavourite = favouritesMap[it.symbol] != null
        }
        return mutableCachedCoins.sortedByFavouritesThen(ALTSharedPreferences.getSort())
    }

    internal fun getMarketData() = MarketData(twentyFourHourMarket, sevenDayMarket, itemsInAverage)

    // Private Functions

    private fun makeCoinListItem(
        inputItem: CoinResponse,
        favouriteCoins: HashMap<String, FavouriteCoin>
    ): CoinListItem {
        val isFavourite = favouriteCoins[inputItem.symbol] != null
        val priceData = PriceData(inputItem)
        val changeData = ChangeData(inputItem)
        twentyFourHourMarket += changeData.percentChange24H?.toFloat() ?: 0f
        sevenDayMarket += changeData.percentChange7D?.toFloat() ?: 0f
        println("count $count now at $twentyFourHourMarket, added ${changeData.percentChange24H}")
        count++
        return CoinListItem(inputItem, priceData, changeData, isFavourite)
    }

    private fun makeFavouritesHashMap(favouriteCoins: MutableList<FavouriteCoin>?): HashMap<String, FavouriteCoin> {
        favouriteCoins ?: return hashMapOf()
        val favouritesMap = HashMap<String, FavouriteCoin>()
        favouriteCoins.forEach {
            favouritesMap[it.symbol] = it
        }
        return favouritesMap
    }
}
