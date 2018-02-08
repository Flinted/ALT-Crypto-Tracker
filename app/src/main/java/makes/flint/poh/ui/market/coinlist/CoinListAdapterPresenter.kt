package makes.flint.poh.ui.market.coinlist

import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import rx.Subscription
import javax.inject.Inject

/**
 * CoinListAdapterPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinListAdapterPresenter @Inject constructor(private var dataController: DataController) :
        CoinListAdapterContractPresenter {
    private var cacheSubscription: Subscription? = null
    private var adapter: CoinListAdapterContractView? = null

    override fun attachView(view: BaseContractView) {
        this.adapter = view as CoinListAdapterContractView
    }

    override fun detachView() {
        adapter = null
        cacheSubscription = null
    }

    override fun onDestroy() {
        detachView()
    }

    private fun subscribeToCache() {
        cacheSubscription = dataController.coinRefreshSubscriber().subscribe {
            onGetCoinListSuccess(it)        }
    }

    override fun initialise() {
        subscribeToCache()
    }

    private fun onGetCoinListSuccess(coinListItems: List<CoinListItem>) {
        adapter?.coinList = coinListItems.toMutableList()
    }

    override fun onFavouriteStateChanged(isFavourite: Boolean, coin: CoinListItem, position: Int) {
        val symbol = coin.symbol
        if (!isFavourite) {
            addCoinAsFavourite(symbol)
            coin.isFavourite = true
        } else {
            removeCoinAsFavourite(symbol)
            coin.isFavourite = false
        }
        adapter?.itemChangedAt(position)
    }

    private fun addCoinAsFavourite(symbol: String) {
        val favouriteCoin = FavouriteCoin(symbol)
        dataController.storeFavouriteCoin(favouriteCoin)
    }

    private fun removeCoinAsFavourite(symbol: String) {
        val favouriteCoin = dataController.getFavouriteCoin(symbol)
        favouriteCoin?.let {
            dataController.deleteFavouriteCoin(it)
        }
    }
}
