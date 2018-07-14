package did.chris.alt.ui.constraintui.layoutCoordinator

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView

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