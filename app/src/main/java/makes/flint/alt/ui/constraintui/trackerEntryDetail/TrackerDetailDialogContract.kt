package makes.flint.alt.ui.constraintui.trackerEntryDetail

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.data.trackerListItem.TrackerTransaction
import rx.Observable

/**
 * TrackerEntryDialogContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

interface TrackerDetailDialogContractView : BaseContractView {
    fun displayTrackerEntry(trackerEntry: TrackerListItem)
    fun initialiseTransactionsList(transactions: MutableList<TrackerTransaction>)
    fun initialiseDeleteButton()
    fun onDeletionOfEntry()
    fun onEntryDeleted(): Observable<Boolean>?
    fun initialiseTransactionsListListener()
    fun initialiseAddAssetButton()
}

interface TrackerDetailDialogContractPresenter : BaseContractPresenter<TrackerDetailDialogContractView> {
    fun initialise(entry: TrackerListItem)
    fun deleteCurrentEntry()
}
