package makes.flint.alt.ui.constraintui.coinlist.coinListAdapter

import makes.flint.alt.base.BaseContractView
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.data.favouriteCoins.FavouriteCoin
import rx.Subscription
import javax.inject.Inject

/**
 * CoinListAdapterPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class CoinListAdapterPresenter @Inject constructor(private var dataController: DataController) :
        CoinListAdapterContractPresenter {

    // Properties

    private var cacheSubscription: Subscription? = null
    private var adapter: CoinListAdapterContractView? = null

    // Lifecycle

    override fun initialise() {
        subscribeToCache()
    }

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

    // Overrides

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

    // Private Functions

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

    private fun subscribeToCache() {
        cacheSubscription = dataController.coinRefreshSubscriber().subscribe {
            onGetCoinListSuccess(it)
        }
    }

    private fun onGetCoinListSuccess(coinListItems: List<CoinListItem>) {
        adapter?.coinList = coinListItems.toMutableList()
        adapter?.emitSortTypeChanged(POHSettings.sortPreference)
    }
}
