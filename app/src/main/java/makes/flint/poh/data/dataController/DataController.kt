package makes.flint.poh.data.dataController

import makes.flint.poh.data.dataController.callbacks.RepositoryCallback
import makes.flint.poh.data.dataController.dataManagers.ApiRepository
import makes.flint.poh.data.dataController.dataManagers.RealmManager
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import javax.inject.Inject

/**
 * DataController
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
open class DataController @Inject constructor(private val apiRepository: ApiRepository,
                                              private val realmManager: RealmManager) {

    fun getCoinList(callback: RepositoryCallback<SummaryCoinResponse>) {
        apiRepository.getCoinList(callback)
    }

    fun getFavouriteCoins(): MutableList<FavouriteCoin> {
        return realmManager.getFavouriteCoins()
    }

    fun storeFavouriteCoin(favouriteCoins: FavouriteCoin) {
        realmManager.copyOrUpdate(favouriteCoins)
    }

    fun getFavouriteCoin(symbol: String): FavouriteCoin? {
        return realmManager.getFavouriteCoin(symbol)
    }

    fun deleteFavouriteCoin(favouriteCoin: FavouriteCoin) {
        realmManager.remove(favouriteCoin)
    }
}
