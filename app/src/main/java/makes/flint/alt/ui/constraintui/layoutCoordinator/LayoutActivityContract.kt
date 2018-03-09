package makes.flint.alt.ui.constraintui.layoutCoordinator

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView

/**
 * LayoutActivityContract
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface LayoutActivityContractView : BaseContractView {
}

interface LayoutActivityContractPresenter : BaseContractPresenter<LayoutActivityContractView> {
    fun emitData()
}