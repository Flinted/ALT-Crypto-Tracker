package did.chris.alt.ui.constraintui.trackerEntryDetail

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
import did.chris.alt.data.trackerListItem.TrackerListItem
import did.chris.alt.factories.TrackerItemFactory
import javax.inject.Inject

/**
 * TrackerDetailDialogPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerDetailDialogPresenter @Inject constructor(private var dataController: DataController,
                                                       private var trackerItemFactory: TrackerItemFactory)
    : BasePresenter<TrackerDetailDialogContractView>(), TrackerDetailDialogContractPresenter {

    // Properties

    private lateinit var trackerEntry: TrackerListItem

    // Lifecycle

    override fun initialise(entry: TrackerListItem) {
        this.trackerEntry = entry
        view?.displayTrackerEntry(trackerEntry)
        view?.initialiseTransactionsList(trackerEntry.transactions)
        view?.initialiseDeleteButton()
        view?.initialiseTransactionsListListener()
        view?.initialiseAddAssetButton()
    }

    override fun initialise() {}

    // Overrides

    override fun deleteCurrentEntry() {
        val id = trackerEntry.id
        dataController.deleteTrackerEntryFor(id)
        view?.onDeletionOfEntry()
    }
}
