package makes.flint.poh.ui.main

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController

/**
 * MainPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MainPresenter(private var dataController: DataController): BasePresenter<MainContractView>()  {

    override fun initialise() {
        view?.initialiseBottomBar()
        view?.initialiseViewPager()
    }
}
