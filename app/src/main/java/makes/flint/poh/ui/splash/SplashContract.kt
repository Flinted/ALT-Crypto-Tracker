package makes.flint.poh.ui.splash

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView

/**
 * SplashContract
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface SplashContractView : BaseContractView {
    fun proceedToMainActivity()
}

interface SplashContractPresenter : BaseContractPresenter<SplashContractView> {
}