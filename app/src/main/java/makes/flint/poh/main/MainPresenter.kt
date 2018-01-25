package makes.flint.poh.main

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.response.APIResponseList
import rx.Subscriber

/**
 * MainPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MainPresenter(private var dataController: DataController): BasePresenter<MainContractView>()  {

    override fun initialise() {
        dataController.getCoinList().subscribe(object : Subscriber<APIResponseList>() {
            override fun onCompleted() {}
            override fun onError(error: Throwable) {}
            override fun onNext(apiResponseList: APIResponseList) {
                onGetCoinListSuccess(apiResponseList)
            }
        })
    }

    private fun onGetCoinListSuccess(apiResponseList: APIResponseList) {
        val data = apiResponseList.data
        data.keys.forEach { coinKey ->
            println("$coinKey retrieved! with response ${data[coinKey].toString()}")
        }
    }


}
