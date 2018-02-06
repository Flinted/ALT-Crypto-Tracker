package makes.flint.poh.ui.tracker.trackerEntryDialog.transactionsList

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import makes.flint.poh.R
import makes.flint.poh.data.tracker.TRANSACTION_BUY
import makes.flint.poh.data.tracker.TRANSACTION_SELL
import makes.flint.poh.data.trackerListItem.TrackerListTransaction
import makes.flint.poh.injection.components.PresenterComponent
import rx.subjects.PublishSubject

/**
 * TransactionsListAdapter
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class TransactionsListAdapter(presenterComponent: PresenterComponent) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        TransactionAdapterContractView {

    private var summaryRefreshRequired: PublishSubject<Boolean> = PublishSubject.create()
    override fun onSummaryRefreshRequired() = summaryRefreshRequired.asObservable()

    var transactionEntries: MutableList<TrackerListTransaction> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var transactionsAdapterPresenter = presenterComponent.provideTransactionsAdapterPresenter()

    init {
        transactionsAdapterPresenter.attachView(this)
        transactionsAdapterPresenter.initialise()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val layout = when (viewType) {
            1 -> R.layout.item_transaction_list_buy
            2 -> R.layout.item_transaction_list_sell
            else -> R.layout.item_transaction_list_mined
        }
        val itemView = layoutInflater.inflate(layout, parent, false)
        return TransactionsListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return transactionEntries.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holder as TransactionsListViewHolder
        val entry = transactionEntries[position]
        viewHolder.date.text = entry.purchaseDate.timeStampISO8601
        viewHolder.quantity.text = entry.quantity.toPlainString()
        viewHolder.pricePaid.text = entry.pricePaid.toPlainString()
        viewHolder.totalCost.text = entry.transactionTotal().toPlainString()
        viewHolder.notes.text = entry.notes
        initialiseDeleteButton(viewHolder.deleteButton, entry, position)
    }

    private fun initialiseDeleteButton(deleteButton: ImageView, entry: TrackerListTransaction, position: Int) {
        val listener = makeDeleteDialogListener(entry, position)
        deleteButton.setOnClickListener {
            val dialog = AlertDialog.Builder(deleteButton.context)
            dialog.apply {
                setMessage("This will delete this transaction irreversibly. Are you sure?")
                setPositiveButton("Yes", listener)
                setNegativeButton("No", listener)
                setCancelable(false)
            }.create()
            dialog.show()
        }
    }

    private fun makeDeleteDialogListener(entry: TrackerListTransaction, position: Int): DialogInterface
    .OnClickListener {
        return DialogInterface.OnClickListener { _, choice ->
            when (choice) {
                DialogInterface.BUTTON_POSITIVE -> transactionsAdapterPresenter.deleteCurrentEntry(entry, position)
                else -> return@OnClickListener
            }
        }
    }

    override fun successfullyDeletedTransaction(entry: TrackerListTransaction) {
        transactionEntries.remove(entry)
        notifyDataSetChanged()
        if (transactionEntries.isEmpty()) {
            summaryRefreshRequired.onNext(false)
            return
        }
        summaryRefreshRequired.onNext(true)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(stringId: Int?) {
    }

    override fun getItemViewType(position: Int): Int {
        val entry = transactionEntries[position]
        return when (entry.transactionType) {
            TRANSACTION_BUY -> 1
            TRANSACTION_SELL -> 2
            else -> super.getItemViewType(position)
        }
    }
}
