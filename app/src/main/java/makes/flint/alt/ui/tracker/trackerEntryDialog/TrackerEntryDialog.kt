package makes.flint.alt.ui.tracker.trackerEntryDialog

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseDialogFragment
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.data.trackerListItem.TrackerTransaction
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.ui.tracker.trackerEntryDialog.transactionsList.TransactionsListAdapter
import rx.subjects.PublishSubject

/**
 * TrackerEntryDialog
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerEntryDialog : BaseDialogFragment(), TrackerEntryDialogContractView {

    // Static Builder
    companion object {
        fun getInstanceFor(entry: TrackerListItem): TrackerEntryDialog {
            val fragment = TrackerEntryDialog()
            fragment.entry = entry
            return fragment
        }
    }

    // Properties
    private lateinit var views: TrackerEntryDialogViewHolder
    private lateinit var entry: TrackerListItem
    private lateinit var transactionsListAdapter: TransactionsListAdapter
    private lateinit var trackerEntryDialogPresenter: TrackerEntryDialogContractPresenter


    // RX Actions
    private var entryDeleted: PublishSubject<Boolean> = PublishSubject.create()

    override fun onEntryDeleted() = entryDeleted.asObservable()

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackerEntryDialogPresenter = getPresenterComponent().provideTrackerEntryDialogPresenter()
        trackerEntryDialogPresenter.attachView(this)
        attachPresenter(trackerEntryDialogPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.dialog_tracker_entry, container)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        this.views = TrackerEntryDialogViewHolder(view)
        trackerEntryDialogPresenter.initialise(entry)
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    // Overrides

    override fun displayTrackerEntry(trackerEntry: TrackerListItem) {
        views.coinName.text = trackerEntry.name
        views.coinSymbol.text = trackerEntry.symbolFormatted
        views.currentAmount.text = trackerEntry.numberOwnedFormatted
        views.currentValueUSD.text = trackerEntry.currentPriceUSDFormatted
        views.currentValueBTC.text = trackerEntry.currentPriceBTCFormatted
        views.percentageChange.text = trackerEntry.percentageChangeFormatted
    }

    override fun initialiseTransactionsList(transactions: MutableList<TrackerTransaction>) {
        transactionsListAdapter = TransactionsListAdapter(getPresenterComponent())
        val layoutManager = LinearLayoutManager(activity)
        views.transactionsList.layoutManager = layoutManager
        views.transactionsList.adapter = transactionsListAdapter
        transactionsListAdapter.transactionEntries = transactions
    }

    override fun initialiseTransactionsListListener() {
        transactionsListAdapter.onSummaryRefreshRequired().subscribe {
            if (!it) {
                trackerEntryDialogPresenter.deleteCurrentEntry()
                return@subscribe
            }
        }
    }

    override fun initialiseDeleteButton() {
        val listener = makeDeleteDialogListener()
        views.deleteButton.setOnClickListener {
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

    override fun onDeletionOfEntry() {
        entryDeleted.onNext(true)
        dismiss()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(stringId: Int?) {
        ErrorHandler.showError(activity, stringId)
    }

    // Private Functions

    private fun makeDeleteDialogListener(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, choice ->
            when (choice) {
                DialogInterface.BUTTON_POSITIVE -> trackerEntryDialogPresenter.deleteCurrentEntry()
                else -> return@OnClickListener
            }
        }
    }
}