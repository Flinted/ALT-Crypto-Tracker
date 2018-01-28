package makes.flint.poh.ui.main

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView

/**
 * MainContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface MainContractView: BaseContractView {
    fun initialiseBottomBar()
}

interface MainContractPresenter<in V:BaseContractView>: BaseContractPresenter<V> {

}