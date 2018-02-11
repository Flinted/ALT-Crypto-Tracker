package makes.flint.poh.ui.settings

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.configuration.SettingsData

/**
 * SettingsContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface SettingsContractView: BaseContractView {
    fun displayCurrentSettings(settings: SettingsData)
}

interface SettingsContractPresenter: BaseContractPresenter<SettingsContractView>