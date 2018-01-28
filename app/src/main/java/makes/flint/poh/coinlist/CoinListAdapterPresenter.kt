package makes.flint.poh.coinlist

import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse
import makes.flint.poh.data.response.coinSummary.compareRank
import rx.Subscriber
import java.util.*
import javax.inject.Inject

/**
 * CoinListAdapterPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinListAdapterPresenter @Inject constructor(private var dataController: DataController) :
        CoinListAdapterContractPresenter {

    private var adapter: CoinListAdapterContractView? = null

    override fun attachView(contractView: BaseContractView) {
        this.adapter = contractView as CoinListAdapterContractView
    }

    override fun detachView() {
        adapter = null
    }

    override fun initialise() {
        dataController.getCoinList().subscribe(object : Subscriber<Array<SummaryCoinResponse>>() {
            override fun onCompleted() {}
            override fun onError(error: Throwable) {}
            override fun onNext(apiResponseList: Array<SummaryCoinResponse>) {
                val mutableListCoinResponse = apiResponseList.toMutableList()
                onGetCoinListSuccess(mutableListCoinResponse)
            }
        })
    }

    private fun onGetCoinListSuccess(apiResponseList: MutableList<SummaryCoinResponse>) {
        val sortedCoins = sortCoins(apiResponseList)
        adapter?.coinList = sortedCoins
    }

    private fun sortCoins(coinList: MutableList<SummaryCoinResponse>): MutableList<SummaryCoinResponse> {
        Collections.sort(coinList, Comparator { coin1, coin2 ->
            return@Comparator coin1.compareRank(coin2)
        })
        return coinList
    }
}
