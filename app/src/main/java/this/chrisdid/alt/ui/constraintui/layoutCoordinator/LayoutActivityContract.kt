package this.chrisdid.alt.ui.constraintui.layoutCoordinator

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView

/**
 * LayoutActivityContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface LayoutActivityContractView : BaseContractView {
    fun loadInitialScreens()
    fun displayError(it: Throwable)
    fun displayRandomLoadingMessage()
}

interface LayoutActivityContractPresenter : BaseContractPresenter<LayoutActivityContractView> {
    fun emitData()
    fun onDestroy()
}