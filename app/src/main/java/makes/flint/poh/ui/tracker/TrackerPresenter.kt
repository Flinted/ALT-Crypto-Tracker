package makes.flint.poh.ui.tracker

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import javax.inject.Inject

/**
 * TrackerPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerPresenter @Inject constructor(private var dataController: DataController) :
        BasePresenter<TrackerContractView>(),
        TrackerContractPresenter {

    override fun initialise() {
        view?.initialiseChartListeners()
        view?.initialiseAddCoinButtonListener()
        view?.initialiseConstraintSets()
        view?.initialiseTrackerList()
        view?.initialiseTrackerListListeners()
        view?.initialiseRefreshListener()
        view?.initialiseSummaryPager()
    }
}
