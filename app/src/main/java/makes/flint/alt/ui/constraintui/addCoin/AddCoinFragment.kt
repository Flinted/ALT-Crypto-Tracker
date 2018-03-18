package makes.flint.alt.ui.constraintui.addCoin

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.tracker.TRANSACTION_BUY
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.ui.constraintui.layoutCoordinator.LayoutCoordinatable
import makes.flint.alt.layoutCoordination.home
import rx.subjects.PublishSubject
import java.util.*

/**
 * AddCoinFragment
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
class AddCoinFragment : BaseFragment(), AddCoinContractView {

    // Properties

    private lateinit var views: AddCoinViewHolder
    private lateinit var addCoinDialogPresenter: AddCoinContractPresenter

    // RX Actions

    private var transactionAdded: PublishSubject<Boolean> = PublishSubject.create()
    override fun onTransactionAdded() = transactionAdded.asObservable()

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        this.addCoinDialogPresenter = getPresenterComponent().provideAddCoinDialogPresenter()
        addCoinDialogPresenter.attachView(this)
        attachPresenter(addCoinDialogPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialog_buy_asset, container, false)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        this.views = AddCoinViewHolder(view)
        attachPresenter(addCoinDialogPresenter)
        addCoinDialogPresenter.attachView(this)
        addCoinDialogPresenter.initialise()
        return view
    }

    // Overrides

    override fun initialiseFABListener() {
        views.addEntryFAB.setOnClickListener {
            makeTrackerEntryData()
        }
    }

    override fun initialiseInputListeners() {
        val listener = makeTextChangeListener()
        views.quantityInput.addTextChangedListener(listener)
        views.priceInput.addTextChangedListener(listener)
        views.feesInput.addTextChangedListener(listener)
    }

    override fun initialiseDateSelectListener() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val listener = makeDatePickerDialog()
        addCoinDialogPresenter.prepareDateSelected(day, month, year)
        views.calendarButton.setOnClickListener {
            val dialog = DatePickerDialog(views.calendarButton.context, listener, year, month, day)
            dialog.datePicker.maxDate = Date().time
            dialog.show()
        }
    }

    override fun setDateSelected(dateString: String) {
        views.dateInput.text = dateString
    }

    override fun initialiseCoinAutoSuggest(autoCompleteSuggestions: List<CoinListItem>) {
        val layout = R.layout.support_simple_spinner_dropdown_item
        val adapter = CoinAutoCompleteAdapter.makeInstanceFor(activity, layout, autoCompleteSuggestions)
        views.assetSearch.setAdapter(adapter)
        views.assetSearch.threshold = 2
        views.assetSearch.setOnItemClickListener { _, _, position, _ ->
            val stringId = adapter.getItem(position)
            val coin = adapter.getCoinListItemForId(stringId)
            views.selectedAsset.text = stringId
            views.assetSearch.text.clear()
            addCoinDialogPresenter.updateSelectedCoin(coin)
        }
    }

    override fun displayUpdatedPurchasePrice(purchasePrice: String) {
        views.purchasePriceDisplay.text = purchasePrice
    }

    override fun displayUpdatedCurrentPrice(currentPrice: String) {
        views.currentPriceDisplay.text = currentPrice
    }

    override fun hideLoading() {
    }

    override fun showError(stringId: Int?) {
        ErrorHandler.showError(activity, stringId)
    }

    override fun showLoading() {
    }

    // Private Functions

    private fun makeDatePickerDialog(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, day ->
            addCoinDialogPresenter.prepareDateSelected(day, month, year)
        }
    }

    private fun makeTextChangeListener(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = updatePriceCalculation()
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }
    }

    private fun updatePriceCalculation() {
        val quantity = views.quantityInput.text.toString()
        val price = views.priceInput.text.toString()
        val fees = views.feesInput.text.toString()
        addCoinDialogPresenter.updatePriceCalculation(quantity, price, fees)
    }

    private fun makeTrackerEntryData() {
        val coinName = views.assetSearch.text.toString()
        val exchange = views.exchangeInput.text.toString()
        val quantity = views.quantityInput.text.toString()
        val price = views.priceInput.text.toString()
        val fees = views.feesInput.text.toString()
        val date = views.dateInput.text.toString()
        val notes = views.notesInput.text.toString()
        val typeId = TRANSACTION_BUY
        addCoinDialogPresenter.onAddEntryRequested(coinName, exchange, quantity, price, fees, date, notes, typeId)
    }

    override fun didAddTrackerEntry() {
        (activity as LayoutCoordinatable).updateLayout(home)
    }
}
