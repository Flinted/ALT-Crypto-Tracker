package makes.flint.alt.ui.tracker.addCoinDialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import makes.flint.alt.R
import makes.flint.alt.base.BaseDialogFragment
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.tracker.TRANSACTION_BUY
import makes.flint.alt.errors.ErrorHandler
import rx.subjects.PublishSubject
import java.util.*

/**
 * AddCoinDialog
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
class AddCoinDialog : BaseDialogFragment(), AddCoinDialogContractView {

    private lateinit var assetSearch: AutoCompleteTextView
    private lateinit var selectedAsset: TextView
    private lateinit var exchangeInput: EditText
    private lateinit var quantityInput: EditText
    private lateinit var priceInput: EditText
    private lateinit var notesInput: EditText
    private lateinit var dateInput: TextView
    private lateinit var calendarButton: ImageView
    private lateinit var currentPriceDisplay: TextView
    private lateinit var purchasePriceDisplay: TextView
    private lateinit var feesInput: EditText
    private lateinit var adBanner: AdView

    private lateinit var addEntryFAB: FloatingActionButton
    private lateinit var addCoinDialogPresenter: AddCoinDialogContractPresenter

    private var transactionAdded: PublishSubject<Boolean> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.addCoinDialogPresenter = getPresenterComponent().provideAddCoinDialogPresenter()
        addCoinDialogPresenter.attachView(this)
        attachPresenter(addCoinDialogPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.dialog_buy_asset, container)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        bindViews(view)
        addCoinDialogPresenter.initialise()
        return view
    }

    override fun onTransactionAdded() = transactionAdded.asObservable()

    private fun bindViews(view: View) {
        this.assetSearch = view.findViewById(R.id.asset_search)
        this.selectedAsset = view.findViewById(R.id.selected_coin)
        this.quantityInput = view.findViewById(R.id.quantity_purchased)
        this.priceInput = view.findViewById(R.id.cost_per_item)
        this.feesInput = view.findViewById(R.id.transaction_fees)
        this.exchangeInput = view.findViewById(R.id.exchange_used)
        this.dateInput = view.findViewById(R.id.date_of_purchase)
        this.notesInput = view.findViewById(R.id.transaction_notes)
        this.calendarButton = view.findViewById(R.id.transaction_date_select)
        this.purchasePriceDisplay = view.findViewById(R.id.value_at_purchase)
        this.currentPriceDisplay = view.findViewById(R.id.value_current)
        this.addEntryFAB = view.findViewById(R.id.add_coin_fab)
        this.adBanner = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adBanner.loadAd(adRequest)
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun initialiseFABListener() {
        addEntryFAB.setOnClickListener {
            makeTrackerEntryData()
        }
    }

    private fun makeTrackerEntryData() {
        val coinName = assetSearch.text.toString()
        val exchange = exchangeInput.text.toString()
        val quantity = quantityInput.text.toString()
        val price = priceInput.text.toString()
        val fees = feesInput.text.toString()
        val date = dateInput.text.toString()
        val notes = notesInput.text.toString()
        val typeId = TRANSACTION_BUY
        addCoinDialogPresenter.onAddEntryRequested(coinName, exchange, quantity, price, fees, date, notes, typeId)
    }

    override fun initialiseInputListeners() {
        val listener = makeTextChangeListener()
        quantityInput.addTextChangedListener(listener)
        priceInput.addTextChangedListener(listener)
        feesInput.addTextChangedListener(listener)
    }

    override fun initialiseDateSelectListener() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val listener = makeDatePickerDialog()
        addCoinDialogPresenter.prepareDateSelected(day, month, year)
        calendarButton.setOnClickListener {
            val dialog = DatePickerDialog(calendarButton.context, listener, year, month, day)
            dialog.datePicker.maxDate = Date().time
            dialog.show()
        }
    }

    private fun makeDatePickerDialog(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, day ->
            addCoinDialogPresenter.prepareDateSelected(day, month, year)
        }
    }

    override fun setDateSelected(dateString: String) {
        this.dateInput.text = dateString
    }

    override fun initialiseCoinAutoSuggest(autoCompleteSuggestions: List<CoinListItem>) {
        val layout = R.layout.support_simple_spinner_dropdown_item
        val adapter = CoinAutoCompleteAdapter.makeInstanceFor(activity, layout, autoCompleteSuggestions)
        assetSearch.setAdapter(adapter)
        assetSearch.threshold = 2
        assetSearch.setOnItemClickListener { _, _, position, _ ->
            val stringId = adapter.getItem(position)
            val coin = adapter.getCoinListItemForId(stringId)
            selectedAsset.text = stringId
            assetSearch.text.clear()
            addCoinDialogPresenter.updateSelectedCoin(coin)
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
        val quantity = quantityInput.text.toString()
        val price = priceInput.text.toString()
        val fees = feesInput.text.toString()
        addCoinDialogPresenter.updatePriceCalculation(quantity, price, fees)
    }

    override fun displayUpdatedPurchasePrice(purchasePrice: String) {
        purchasePriceDisplay.text = purchasePrice
    }

    override fun displayUpdatedCurrentPrice(currentPrice: String) {
        currentPriceDisplay.text = currentPrice
    }

    private fun updateViewsForTransactionType(isEnabled: Boolean, color: Int) {
        feesInput.isEnabled = isEnabled
        exchangeInput.isEnabled = isEnabled
        priceInput.isEnabled = isEnabled
        feesInput.setBackgroundColor(color)
        exchangeInput.setBackgroundColor(color)
        priceInput.setBackgroundColor(color)
    }

    override fun hideLoading() {
    }

    override fun showError(stringId: Int?) {
        ErrorHandler.showError(activity, stringId)
    }

    override fun showLoading() {
    }

    override fun endDialog() {
        addCoinDialogPresenter.detachView()
        transactionAdded.onNext(true)
        dismiss()
    }
}
