package makes.flint.poh.ui.tracker.trackerEntryDialog

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.trackerListItem.TrackerListItem
import makes.flint.poh.data.trackerListItem.TrackerTransaction
import rx.Observable

/**
 * TrackerEntryDialogContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */

interface TrackerEntryDialogContractView : BaseContractView {
    fun displayTrackerEntry(trackerEntry: TrackerListItem)
    fun initialiseTransactionsList(transactions: MutableList<TrackerTransaction>)
    fun initialiseDeleteButton()
    fun onDeletionOfEntry()
    fun onEntryDeleted(): Observable<Boolean>?
    fun initialiseTransactionsListListener()
}

interface TrackerEntryDialogContractPresenter : BaseContractPresenter<TrackerEntryDialogContractView> {
    fun initialise(entry: TrackerListItem)
    fun deleteCurrentEntry()
}
