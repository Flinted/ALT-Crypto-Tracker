package makes.flint.poh.ui.tracker.trackerEntryDialog.transactionsList

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.trackerListItem.TrackerListTransaction
import rx.Observable

/**
 * TransactionAdapterContract
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface TransactionAdapterContractView : BaseContractView {
    fun successfullyDeletedTransaction(entry: TrackerListTransaction)
    fun onSummaryRefreshRequired(): Observable<Boolean>?
}

interface TransactionAdapterContractPresenter : BaseContractPresenter<TransactionAdapterContractView> {
    fun deleteCurrentEntry(entry: TrackerListTransaction, position: Int)
}