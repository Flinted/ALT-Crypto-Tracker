package makes.flint.poh.ui.tracker.trackerEntryDialog

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import makes.flint.poh.R
import makes.flint.poh.base.BaseDialogFragment
import makes.flint.poh.data.trackerListItem.TrackerListItem
import makes.flint.poh.data.trackerListItem.TrackerListTransaction
import makes.flint.poh.errors.ErrorHandler
import makes.flint.poh.ui.tracker.trackerEntryDialog.transactionsList.TransactionsListAdapter
import rx.subjects.PublishSubject

/**
 * TrackerEntryDialog
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerEntryDialog : BaseDialogFragment(), TrackerEntryDialogContractView {
    companion object {
        fun getInstanceFor(entry: TrackerListItem): TrackerEntryDialog {
            val fragment = TrackerEntryDialog()
            fragment.entry = entry
            return fragment
        }
    }

    private lateinit var deleteButton: ImageView
    private lateinit var coinName: TextView
    private lateinit var coinSymbol: TextView
    private lateinit var currentAmount: TextView
    private lateinit var currentValueUSD: TextView
    private lateinit var currentValueBTC: TextView
    private lateinit var percentageChange: TextView
    private lateinit var transactionsList: RecyclerView
    private lateinit var trackerEntryDialogPresenter: TrackerEntryDialogContractPresenter
    private lateinit var entry: TrackerListItem

    private lateinit var transactionsListAdapter: TransactionsListAdapter

    // RX Actions
    private var entryDeleted: PublishSubject<Boolean> = PublishSubject.create()

    override fun onEntryDeleted() = entryDeleted.asObservable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackerEntryDialogPresenter = getPresenterComponent().provideTrackerEntryDialogPresenter()
        trackerEntryDialogPresenter.attachView(this)
        attachPresenter(trackerEntryDialogPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.dialog_tracker_entry, container)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        bindViews(view)
        trackerEntryDialogPresenter.initialise(entry)
        return view
    }

    private fun bindViews(view: View) {
        this.deleteButton = view.findViewById(R.id.dialog_tracker_delete)
        this.coinName = view.findViewById(R.id.dialog_tracker_coin_name)
        this.coinSymbol = view.findViewById(R.id.dialog_tracker_coin_symbol)
        this.currentAmount = view.findViewById(R.id.dialog_tracker_number_owned)
        this.currentValueUSD = view.findViewById(R.id.dialog_tracker_current_value_USD)
        this.currentValueBTC = view.findViewById(R.id.dialog_tracker_current_value_BTC)
        this.transactionsList = view.findViewById(R.id.dialog_tracker_transactions_recycler)
        this.percentageChange = view.findViewById(R.id.dialog_tracker_percentage_change)
    }

    override fun displayTrackerEntry(trackerEntry: TrackerListItem) {
        coinName.text = trackerEntry.name
        coinSymbol.text = trackerEntry.getSymbolFormatted()
        currentAmount.text = trackerEntry.getNumberOwnedFormatted()
        currentValueUSD.text = trackerEntry.getCurrentValueUSDFormatted()
        currentValueBTC.text = trackerEntry.getCurrentValueBTCFormatted()
        percentageChange.text = trackerEntry.getPercentageChangeFormatted()
    }

    override fun initialiseTransactionsList(transactions: MutableList<TrackerListTransaction>) {
        transactionsListAdapter = TransactionsListAdapter(getPresenterComponent())
        val layoutManager = LinearLayoutManager(activity)
        transactionsList.layoutManager = layoutManager
        transactionsList.adapter = transactionsListAdapter
        transactionsListAdapter.transactionEntries = transactions
    }

    override fun initialiseTransactionsListListener() {
        transactionsListAdapter.onSummaryRefreshRequired().subscribe(){
            if(!it) {
                trackerEntryDialogPresenter.deleteCurrentEntry()
                return@subscribe
            }
        }
    }

    override fun initialiseDeleteButton() {
        val listener = makeDeleteDialogListener()
        deleteButton.setOnClickListener {
            val dialog = AlertDialog.Builder(activity)
            dialog.apply {
                setMessage("This will delete ALL transactions for this asset irreversibly. Are you sure?")
                setPositiveButton("Yes", listener)
                setNegativeButton("No", listener)
                setCancelable(false)
            }.create()
            dialog.show()
        }
    }

    private fun makeDeleteDialogListener(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, choice ->
            when (choice) {
                DialogInterface.BUTTON_POSITIVE -> trackerEntryDialogPresenter.deleteCurrentEntry()
                else -> return@OnClickListener
            }
        }
    }

    override fun onDeletionOfEntry() {
        entryDeleted.onNext(true)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(stringId: Int?) {
        ErrorHandler.showError(activity, stringId)
    }
}