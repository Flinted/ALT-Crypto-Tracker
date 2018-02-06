package makes.flint.poh.ui.tracker.trackerEntryDialog

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.trackerListItem.TrackerListItem
import makes.flint.poh.factories.TrackerItemFactory
import javax.inject.Inject

/**
 * TrackerEntryDialogPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerEntryDialogPresenter @Inject constructor(private var dataController: DataController,
                                                      private var trackerItemFactory: TrackerItemFactory)
    : BasePresenter<TrackerEntryDialogContractView>(), TrackerEntryDialogContractPresenter {

    private lateinit var trackerEntry: TrackerListItem

    override fun initialise(entry: TrackerListItem) {
        this.trackerEntry = entry
        view?.displayTrackerEntry(trackerEntry)
        view?.initialiseTransactionsList(trackerEntry.transactions)
        view?.initialiseDeleteButton()
        view?.initialiseTransactionsListListener()
    }

    override fun initialise() {}

    override fun deleteCurrentEntry() {
        val id = trackerEntry.id
        dataController.deleteTrackerEntryFor(id)
        view?.onDeletionOfEntry()
    }
}