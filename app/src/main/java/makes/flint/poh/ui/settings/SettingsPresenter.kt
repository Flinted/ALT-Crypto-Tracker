package makes.flint.poh.ui.settings

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import javax.inject.Inject

/**
 * SettingsPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SettingsPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<SettingsContractView>(),
        SettingsContractPresenter {

    override fun initialise() {
    }
}