package makes.flint.alt.ui.tracker.trackerEntryDialog.transactionsList

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.data.trackerListItem.TrackerTransaction
import javax.inject.Inject

/**
 * TransactionsAdapterPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TransactionsAdapterPresenter @Inject constructor(private var dataController: DataController) :
        BasePresenter<TransactionAdapterContractView>(), TransactionAdapterContractPresenter {

    override fun deleteCurrentEntry(entry: TrackerTransaction, position: Int) {
        val idToDelete = entry.dataId
        dataController.deleteTransactionFor(idToDelete)
        view?.successfullyDeletedTransaction(entry)
    }
    override fun initialise() {
    }
}