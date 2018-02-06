package makes.flint.poh.ui.main

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.configuration.POHSettings
import makes.flint.poh.data.dataController.DataController

/**
 * MainPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MainPresenter(private var dataController: DataController): BasePresenter<MainContractView>()  {

    override fun initialise() {
        val startingTab = POHSettings.startingScreen
        view?.initialiseViewPager()
        view?.initialiseBottomBar(startingTab)
    }
}
