package makes.flint.poh.ui.coinlist

import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.response.CoinResponse
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import makes.flint.poh.factories.CoinListItemFactory
import rx.Subscriber
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
        dataController.getCoinList().subscribe(object : Subscriber<Array<SummaryCoinResponse>>() {
            override fun onCompleted() {}
            override fun onError(error: Throwable) {
                adapter?.hideLoading()
                throw error
            }
            override fun onNext(apiResponseList: Array<SummaryCoinResponse>) {
                onGetCoinListSuccess(apiResponseList)
            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    private fun onGetCoinListSuccess(apiResponseList: Array<SummaryCoinResponse>) {
        adapter?.hideLoading()
        val mutableListCoinResponse = apiResponseList.toMutableList() as MutableList<CoinResponse>
        val coinListItems = CoinListItemFactory().makeCoinListItems(mutableListCoinResponse)
        adapter?.coinList = coinListItems
    }
}
