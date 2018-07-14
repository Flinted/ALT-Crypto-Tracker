package did.chris.alt.ui.constraintui.trackerEntryDetail

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.trackerListItem.TrackerListItem
import did.chris.alt.data.trackerListItem.TrackerTransaction
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
