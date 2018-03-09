package makes.flint.alt.ui.constraintui.layoutCoordinator

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import javax.inject.Inject

/**
 * LayoutPresenter
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class LayoutPresenter @Inject constructor(private val dataController: DataController) : BasePresenter<LayoutActivityContractView>(),
        LayoutActivityContractPresenter {

    override fun emitData() {
        dataController.refreshRequested()
    }

    override fun initialise() {
    }
}