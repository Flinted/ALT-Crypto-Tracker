package makes.flint.alt.ui.settings

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import javax.inject.Inject

/**
 * SettingsPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SettingsPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<SettingsContractView>(),
        SettingsContractPresenter {

    override fun initialise() {
        val settings = dataController.getSettings() ?: return
        view?.displayCurrentSettings(settings)
    }
}