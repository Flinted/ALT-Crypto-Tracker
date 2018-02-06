package makes.flint.poh.ui.tracker.trackerEntryDialog

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.trackerListItem.TrackerListItem
import makes.flint.poh.data.trackerListItem.TrackerListTransaction
import rx.Observable

/**
 * TrackerEntryDialogContract
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */

interface TrackerEntryDialogContractView : BaseContractView {
    fun displayTrackerEntry(trackerEntry: TrackerListItem)
    fun initialiseTransactionsList(transactions: MutableList<TrackerListTransaction>)
    fun initialiseDeleteButton()
    fun onDeletionOfEntry()
    fun onEntryDeleted(): Observable<Boolean>?
    fun initialiseTransactionsListListener()
}

interface TrackerEntryDialogContractPresenter : BaseContractPresenter<TrackerEntryDialogContractView> {
    fun initialise(entry: TrackerListItem)
    fun deleteCurrentEntry()
}
