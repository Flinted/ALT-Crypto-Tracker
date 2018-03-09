package makes.flint.alt.ui.tracker.trackerEntryDialog.transactionsList

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.trackerListItem.TrackerTransaction
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