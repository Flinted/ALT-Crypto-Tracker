package makes.flint.poh.ui.tracker.trackerEntryDialog.transactionsList

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.trackerListItem.TrackerTransaction
import rx.Observable

/**
 * TransactionAdapterContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface TransactionAdapterContractView : BaseContractView {
    fun successfullyDeletedTransaction(entry: TrackerTransaction)
    fun onSummaryRefreshRequired(): Observable<Boolean>?
}

interface TransactionAdapterContractPresenter : BaseContractPresenter<TransactionAdapterContractView> {
    fun deleteCurrentEntry(entry: TrackerTransaction, position: Int)
}