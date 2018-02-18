package makes.flint.alt.ui.splash

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView

/**
 * SplashContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface SplashContractView : BaseContractView {
    fun proceedToMainActivity()
    fun proceedToOnboardActivity()
}

interface SplashContractPresenter : BaseContractPresenter<SplashContractView> {
}