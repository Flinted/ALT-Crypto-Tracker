package makes.flint.alt.ui.snapshot

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import javax.inject.Inject

/**
 * SnapShotPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SnapShotPresenter @Inject constructor(private val dataController: DataController)
    : BasePresenter<SnapShotContractView>(), SnapShotContractPresenter {

    override fun initialise() {
    }
}