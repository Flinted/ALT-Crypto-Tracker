package did.chris.alt.ui.constraintui.trackerEntryDetail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import did.chris.alt.R
import did.chris.alt.base.BaseDialogFragment
import did.chris.alt.data.trackerListItem.TrackerListItem
import did.chris.alt.data.trackerListItem.TrackerTransaction
import did.chris.alt.errors.ErrorHandler
import did.chris.alt.ui.constraintui.addCoin.AddCoinDialogFragment
import did.chris.alt.ui.tracker.trackerEntryDialog.transactionsList.TransactionsListAdapter
import rx.subjects.PublishSubject

/**
 * TrackerDetailDialog
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class TrackerDetailDialog : BaseDialogFragment(), TrackerDetailDialogContractView {

    // Static Builder
    companion object {
        fun getInstanceFor(entry: TrackerListItem): TrackerDetailDialog {
            val fragment = TrackerDetailDialog()
            fragment.entry = entry
            return fragment
        }
    }

    // Properties
    private lateinit var views: TrackerDetailDialogViewHolder
    private lateinit var entry: TrackerListItem
    private lateinit var transactionsListAdapter: TransactionsListAdapter
    private lateinit var trackerEntryDialogPresenter: TrackerDetailDialogContractPresenter


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

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater?.inflate(R.layout.dialog_tracker_detail, container)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        this.views = TrackerDetailDialogViewHolder(view)
        trackerEntryDialogPresenter.initialise(entry)
        initialiseBackButton()
        return view
    }

    private fun initialiseBackButton() {
        views.backButton.setOnClickListener{
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
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

    override fun initialiseAddAssetButton() {
        views.addAssetButton.setOnClickListener { _ ->
            val coin = entry.getSearchId()
            val fragment = AddCoinDialogFragment.createForAsset(coin)
            val fragmentManager = activity?.fragmentManager
            val shownCoinDetail = fragmentManager?.findFragmentByTag("AddCoinDialog")
            shownCoinDetail?.let { shownDialog ->
                fragmentManager.beginTransaction().remove(shownDialog).commit()
            }
            fragment.show(fragmentManager, "AddCoinDialog")
            dismiss()
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
                else                            -> return@OnClickListener
            }
        }
    }
}