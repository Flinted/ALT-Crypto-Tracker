package makes.flint.poh.ui.tracker.trackerEntryDialog.transactionsList

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.trackerListItem.TrackerListTransaction
import javax.inject.Inject

/**
 * TransactionsAdapterPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TransactionsAdapterPresenter @Inject constructor(private var dataController: DataController) :
        BasePresenter<TransactionAdapterContractView>(), TransactionAdapterContractPresenter {

    override fun deleteCurrentEntry(entry: TrackerListTransaction, position: Int) {
        val idToDelete = entry.dataId
        dataController.deleteTransactionFor(idToDelete)
        view?.successfullyDeletedTransaction(entry)
    }
    override fun initialise() {
    }
}