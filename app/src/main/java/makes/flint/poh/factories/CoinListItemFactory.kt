package makes.flint.poh.factories

import makes.flint.poh.configuration.POHSettings
import makes.flint.poh.data.coinListItem.ChangeData
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.coinListItem.PriceData
import makes.flint.poh.data.coinListItem.marketData.MarketData
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import makes.flint.poh.data.response.CoinResponse
import makes.flint.poh.ui.interfaces.sortedByFavouritesThen
import javax.inject.Inject

/**
 * CoinListItemFactory
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinListItemFactory @Inject constructor() {

    private var oneHourMarket = 0f
    private var twentyFourHourMarket = 0f
    private var sevenDayMarket = 0f
    private var itemsInAverage = 0

    // Internal Functions
    internal fun makeCoinListItems(inputItems: MutableList<CoinResponse>,
                                   favouriteCoins: MutableList<FavouriteCoin>?
    ): MutableList<CoinListItem> {
        oneHourMarket = 0f
        twentyFourHourMarket = 0f
        sevenDayMarket = 0f
        val coinListMap = makeFavouritesHashMap(favouriteCoins)
        val coinListItems = inputItems.map { makeCoinListItem(it, coinListMap) } as MutableList<CoinListItem>
        itemsInAverage = coinListItems.size
        return coinListItems.sortedByFavouritesThen(POHSettings.sortPreference)
    }

    private fun makeFavouritesHashMap(favouriteCoins: MutableList<FavouriteCoin>?): HashMap<String, FavouriteCoin> {
        favouriteCoins ?: return hashMapOf()
        val favouritesMap = HashMap<String, FavouriteCoin>()
        favouriteCoins.forEach {
            favouritesMap[it.symbol] = it
        }
        return favouritesMap
    }

    fun updateFavouriteCoins(cachedCoins: List<CoinListItem>?, favouriteCoins: MutableList<FavouriteCoin>):
            MutableList<CoinListItem>? {
        val mutableCachedCoins = cachedCoins?.toMutableList() ?: return mutableListOf()
        val favouritesMap = makeFavouritesHashMap(favouriteCoins)
        cachedCoins.forEach {
            it.isFavourite = favouritesMap[it.symbol] != null
        }
        return mutableCachedCoins.sortedByFavouritesThen(POHSettings.sortPreference)
    }

    // Private Functions
    private fun makeCoinListItem(inputItem: CoinResponse,
                                 favouriteCoins: HashMap<String, FavouriteCoin>
    ): CoinListItem {
        val isFavourite = favouriteCoins[inputItem.symbol] != null
        val priceData = PriceData(inputItem)
        val changeData = ChangeData(inputItem)
        oneHourMarket += changeData.percentChange1H?.toFloat() ?: 0f
        twentyFourHourMarket += changeData.percentChange24H?.toFloat() ?: 0f
        sevenDayMarket += changeData.percentChange7D?.toFloat() ?: 0f
        return CoinListItem(inputItem, priceData, changeData, isFavourite)
    }

    fun getMarketData(): MarketData {
        return MarketData(oneHourMarket, twentyFourHourMarket, sevenDayMarket, itemsInAverage)
    }
}
