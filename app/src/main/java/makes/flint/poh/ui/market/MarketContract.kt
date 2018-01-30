package makes.flint.poh.ui.market

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView

/**
 * MarketContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface MarketContractView: BaseContractView {
    fun initialiseListAdapter()
    fun initialiseSwipeRefreshListener()
    fun initialiseScrollListener()
    fun initialiseFABonClick()
    fun initialiseSyncListener()
}

interface MarketContractPresenter: BaseContractPresenter<MarketContractView> {
}