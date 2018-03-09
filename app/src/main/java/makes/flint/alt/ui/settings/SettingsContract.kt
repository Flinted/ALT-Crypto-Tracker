package makes.flint.alt.ui.settings

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.configuration.SettingsData

/**
 * SettingsContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface SettingsContractView: BaseContractView {
    fun displayCurrentSettings(settings: SettingsData)
}

interface SettingsContractPresenter: BaseContractPresenter<SettingsContractView>