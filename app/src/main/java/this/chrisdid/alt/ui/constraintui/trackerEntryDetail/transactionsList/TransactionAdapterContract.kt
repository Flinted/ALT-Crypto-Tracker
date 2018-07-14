package this.chrisdid.alt.ui.tracker.trackerEntryDialog.transactionsList

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView
import this.chrisdid.alt.data.trackerListItem.TrackerTransaction
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