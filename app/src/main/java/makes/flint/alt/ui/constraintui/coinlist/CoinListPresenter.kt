package makes.flint.alt.ui.constraintui.coinlist

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import javax.inject.Inject

/**
 * CoinListPresenter
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class CoinListPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<CoinListContractView>(), CoinListContractPresenter {
    override fun initialise() {
        view?.initialiseListAdapter()
        view?.initialiseSwipeRefreshListener()
        view?.initialiseScrollListener()
        view?.initialiseFABonClick()
    }

    override fun refresh() {
        dataController.refreshRequested()
    }

    override fun onCoinSelected(coinSymbol: String) {
        view?.showDialogForCoin(coinSymbol)
    }
}