package makes.flint.alt.ui.constraintui.layoutCoordinator

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView

/**
 * LayoutActivityContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface LayoutActivityContractView : BaseContractView {
    fun loadInitialScreens()
    fun displayError(it: Throwable)
}

interface LayoutActivityContractPresenter : BaseContractPresenter<LayoutActivityContractView> {
    fun emitData()
    fun onDestroy()
}