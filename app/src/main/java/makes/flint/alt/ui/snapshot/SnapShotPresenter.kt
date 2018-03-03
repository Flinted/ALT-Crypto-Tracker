package makes.flint.alt.ui.snapshot

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.factories.SnapShotFactory
import javax.inject.Inject

/**
 * SnapShotPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SnapShotPresenter @Inject constructor(private val dataController: DataController, private val snapShotFactory: SnapShotFactory)
    : BasePresenter<SnapShotContractView>(), SnapShotContractPresenter {

    // Lifecycle

    override fun initialise() {
        val trackerEntries = dataController.getAllTrackerEntries()
        val currentSnapShot = snapShotFactory.makeSnapShot(trackerEntries)
        val snapShots = listOf(currentSnapShot)
        view?.displaySnapShots(snapShots)
    }
}