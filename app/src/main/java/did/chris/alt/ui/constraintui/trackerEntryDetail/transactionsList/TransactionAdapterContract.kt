package did.chris.alt.ui.tracker.trackerEntryDialog.transactionsList

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.trackerListItem.TrackerTransaction
import rx.Observable

/**
 * TransactionAdapterContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface TransactionAdapterContractView : BaseContractView {
    fun successfullyDeletedTransaction(entry: TrackerTransaction)
    fun onSummaryRefreshRequired(): Observable<Boolean>?
}

interface TransactionAdapterContractPresenter : BaseContractPresenter<TransactionAdapterContractView> {
    fun deleteCurrentEntry(entry: TrackerTransaction, position: Int)
}