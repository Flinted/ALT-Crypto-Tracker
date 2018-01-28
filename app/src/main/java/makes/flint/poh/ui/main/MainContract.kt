package makes.flint.poh.ui.main

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView

/**
 * MainContract
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface MainContractView: BaseContractView {
    fun initialiseListAdapter()
    fun setSwipeRefreshListener()
}

interface MainContractPresenter: BaseContractPresenter<BaseContractView> {

}