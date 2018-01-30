package makes.flint.poh.ui.coinlist

import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.callbacks.RepositoryCallback
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import makes.flint.poh.data.response.CoinResponse
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import makes.flint.poh.factories.CoinListItemFactory
import javax.inject.Inject

/**
 * CoinListAdapterPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinListAdapterPresenter @Inject constructor(private var dataController: DataController) :
        CoinListAdapterContractPresenter {

    private var adapter: CoinListAdapterContractView? = null

    override fun attachView(view: BaseContractView) {
        this.adapter = view as CoinListAdapterContractView
    }

    override fun detachView() {
        adapter = null
    }

    override fun initialise() {
        adapter?.showLoading()
        dataController.getCoinList(object : RepositoryCallback<SummaryCoinResponse> {
            override fun onError(error: Throwable) {
                adapter?.hideLoading()
            }

            override fun onRetrieve(refreshed: Boolean, lastSync: String, results: List<SummaryCoinResponse>) {
                if (!refreshed) {
                    adapter?.hideLoading()
                    adapter?.notRefreshed()
                    onGetCoinListSuccess(results)
                    return
                }
                onGetCoinListSuccess(results)
                updateLastSync(lastSync)
            }
        })
    }

    private fun updateLastSync(lastSync: String) {
        adapter?.hideLoading()
        adapter?.updateLastSync(lastSync)
    }

    @Suppress("UNCHECKED_CAST")
    private fun onGetCoinListSuccess(apiResponseList: List<SummaryCoinResponse>) {
        val mutableListCoinResponse = apiResponseList.toMutableList<CoinResponse>()
        val favouriteCoins = dataController.getFavouriteCoins()
        val coinListItems = CoinListItemFactory().makeCoinListItems(mutableListCoinResponse, favouriteCoins)
        adapter?.coinList = coinListItems
    }

    fun onLongPress(coin: CoinListItem, position: Int): Boolean {
        val symbol = coin.symbol
        val favouriteCoin = dataController.getFavouriteCoin(symbol)
        toggleCoinStatus(favouriteCoin, coin)
        adapter?.itemChangedAt(position)
        return true
    }

    private fun toggleCoinStatus(favouriteCoin: FavouriteCoin?, coin: CoinListItem) {
        favouriteCoin?.let {
            dataController.deleteFavouriteCoin(favouriteCoin)
            coin.isFavourite = false
            return
        }
        dataController.storeFavouriteCoin(FavouriteCoin(coin.symbol))
        coin.isFavourite = true
    }
}

