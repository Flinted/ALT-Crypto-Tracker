package makes.flint.poh.ui.market

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import javax.inject.Inject

/**
 * MarketPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketPresenter @Inject constructor(private var dataController: DataController)
    : BasePresenter<MarketContractView>() {

    override fun initialise() {
        view?.initialiseListAdapter()
        view?.initialiseSwipeRefreshListener()
        view?.initialiseScrollListener()
        view?.initialiseFABonClick()
        view?.initialiseSyncListener()
    }
}