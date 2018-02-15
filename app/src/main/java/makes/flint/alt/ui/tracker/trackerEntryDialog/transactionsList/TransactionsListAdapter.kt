package makes.flint.alt.ui.tracker.trackerEntryDialog.transactionsList

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import makes.flint.alt.R
import makes.flint.alt.data.trackerListItem.TrackerTransaction
import makes.flint.alt.data.trackerListItem.TrackerTransactionBuy
import makes.flint.alt.data.trackerListItem.TrackerTransactionSale
import makes.flint.alt.injection.components.PresenterComponent
import makes.flint.alt.utility.DateFormatter
import org.threeten.bp.ZonedDateTime
import rx.subjects.PublishSubject

/**
 * TransactionsListAdapter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TransactionsListAdapter(presenterComponent: PresenterComponent) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        TransactionAdapterContractView {

    // RX Actions
    private var summaryRefreshRequired: PublishSubject<Boolean> = PublishSubject.create()

    override fun onSummaryRefreshRequired() = summaryRefreshRequired.asObservable()

    // Internal properties

    internal var transactionEntries: MutableList<TrackerTransaction> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // Private properties

    private var transactionsAdapterPresenter = presenterComponent.provideTransactionsAdapterPresenter()

    // Lifecycle
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
        val timeStamp = entry.purchaseDate.timeStampISO8601
        val formattedDate = ZonedDateTime.parse(timeStamp).format(DateFormatter.DATE)
        viewHolder.date.text = formattedDate
        viewHolder.quantity.text = entry.quantity.toPlainString()
        viewHolder.pricePaid.text = entry.pricePaidFormatted()
        viewHolder.totalCost.text = entry.transactionTotalFormatted()
        viewHolder.notes.text = entry.notes
        initialiseDeleteButton(viewHolder.deleteButton, entry, position)
    }

    private fun initialiseDeleteButton(deleteButton: ImageView, entry: TrackerTransaction, position: Int) {
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

    private fun makeDeleteDialogListener(entry: TrackerTransaction, position: Int): DialogInterface
    .OnClickListener {
        return DialogInterface.OnClickListener { _, choice ->
            when (choice) {
                DialogInterface.BUTTON_POSITIVE -> transactionsAdapterPresenter.deleteCurrentEntry(entry, position)
                else -> return@OnClickListener
            }
        }
    }

    override fun successfullyDeletedTransaction(entry: TrackerTransaction) {
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
        return when (entry) {
            is TrackerTransactionBuy -> 1
            is TrackerTransactionSale -> 2
            else -> super.getItemViewType(position)
        }
    }
}
