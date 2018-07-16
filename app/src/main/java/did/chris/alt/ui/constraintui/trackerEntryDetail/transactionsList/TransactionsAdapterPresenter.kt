package did.chris.alt.ui.tracker.trackerEntryDialog.transactionsList

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
import did.chris.alt.data.trackerListItem.TrackerTransaction
import javax.inject.Inject

class TransactionsAdapterPresenter @Inject constructor(private var dataController: DataController) :
        BasePresenter<TransactionAdapterContractView>(), TransactionAdapterContractPresenter {

    // Lifecycle
    override fun initialise() {
    }

    // Overrides
    override fun deleteCurrentEntry(entry: TrackerTransaction, position: Int) {
        val idToDelete = entry.dataId
        dataController.deleteTransactionFor(idToDelete)
        view?.successfullyDeletedTransaction(entry)
    }
}
