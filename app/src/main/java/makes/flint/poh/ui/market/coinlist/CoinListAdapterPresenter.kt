package makes.flint.poh.ui.market.coinlist

import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.callbacks.RepositoryCallbackArray
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import makes.flint.poh.errors.ErrorHandler
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
        cacheSubscription = dataController.refreshSubscriber().subscribe {
            if (!it) {
                return@subscribe
            }
            refresh()
        }
    }

    override fun initialise() {
        subscribeToCache()
        refresh()
    }

    fun refresh() {
        adapter?.showLoading()
        val callback = makeRepositoryCallback()
        dataController.getCoinListNew(callback)
    }

    private fun makeRepositoryCallback(): RepositoryCallbackArray<CoinListItem> {
        return object : RepositoryCallbackArray<CoinListItem> {
            override fun onError(error: Throwable) {
                adapter?.showError(ErrorHandler.API_FAILURE)
                adapter?.hideLoading()
            }

            override fun onRetrieve(refreshed: Boolean, lastSync: String, results: List<CoinListItem>) {
                if (!refreshed) {
                    adapter?.hideLoading()
                    adapter?.notRefreshed()
                    recheckFavourites()
                    return
                }
                updateLastSync(lastSync)
                onGetCoinListSuccess(results)
            }
        }
    }

    private fun updateLastSync(lastSync: String) {
        adapter?.hideLoading()
        adapter?.updateLastSync(lastSync)
    }

    private fun onGetCoinListSuccess(coinListItems: List<CoinListItem>) {
        adapter?.coinList = coinListItems.toMutableList()
    }

    private fun recheckFavourites() {
        dataController.getCoinListNew(object : RepositoryCallbackArray<CoinListItem> {
            override fun onError(error: Throwable) {
                ErrorHandler.API_FAILURE
            }

            override fun onRetrieve(refreshed: Boolean, lastSync: String, results: List<CoinListItem>) {
                if (results.isEmpty()) {
                    initialise()
                    return
                }
                adapter?.coinList = results.toMutableList()
            }
        })
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
