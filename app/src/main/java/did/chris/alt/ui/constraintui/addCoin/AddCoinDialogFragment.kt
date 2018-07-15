package did.chris.alt.ui.constraintui.addCoin

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import did.chris.alt.R
import did.chris.alt.base.BaseDialogFragment
import did.chris.alt.data.coinListItem.CoinListItem
import did.chris.alt.data.tracker.TRANSACTION_BUY
import did.chris.alt.errors.ErrorHandler
import rx.subjects.PublishSubject
import java.util.*

private const val ASSET_KEY = "assetKey"

class AddCoinDialogFragment : BaseDialogFragment(), AddCoinContractView {

    companion object {
        fun createForAsset(asset: String?): AddCoinDialogFragment {
            val bundle = Bundle()
            bundle.putString(ASSET_KEY, asset)
            val fragment = AddCoinDialogFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    // Properties

    private lateinit var views: AddCoinViewHolder
    private lateinit var addCoinDialogPresenter: AddCoinContractPresenter
    private lateinit var adapter: CoinAutoCompleteAdapter
    private var preSelectAsset: String? = null

    // RX Actions

    private var transactionAdded: PublishSubject<Boolean> = PublishSubject.create()
    override fun onTransactionAdded() = transactionAdded.asObservable()

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preSelectAsset = arguments?.getString(ASSET_KEY)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        this.addCoinDialogPresenter = getPresenterComponent().provideAddCoinDialogPresenter()
        addCoinDialogPresenter.attachView(this)
        attachPresenter(addCoinDialogPresenter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_buy_asset, container, false)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        this.views = AddCoinViewHolder(view)
        attachPresenter(addCoinDialogPresenter)
        addCoinDialogPresenter.attachView(this)
        addCoinDialogPresenter.initialise()
        initialiseBackButton()
        return view
    }

    private fun initialiseBackButton() {
        views.backButton.setOnClickListener {
            hideKeyboard(views.addEntryButton.windowToken)
            dismiss()
        }
    }

    // Overrides
    override fun initialiseFABListener() {
        views.addEntryButton.setOnClickListener {
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
        adapter = CoinAutoCompleteAdapter.makeInstanceFor(
            activity,
            layout,
            autoCompleteSuggestions
        )
        views.assetSearch.setAdapter(adapter)
        views.assetSearch.threshold = 2
        views.assetSearch.setOnItemClickListener { _, _, position, _ ->
            val stringId = adapter.getItem(position)
            setAssetForId(stringId)
        }
        showKeyboard()
        if (checkForPresetAsset()) {
            views.quantityInput.requestFocus()
            return
        }
        views.assetSearch.requestFocus()
    }

    private fun checkForPresetAsset(): Boolean {
        val assetId = preSelectAsset ?: return false
        setAssetForId(assetId)
        return true
    }

    private fun setAssetForId(assetId: String) {
        val coin = adapter.getCoinListItemForId(assetId)
        views.selectedAsset.text = assetId
        views.assetSearch.text.clear()
        addCoinDialogPresenter.updateSelectedCoin(coin)
        updatePriceCalculation()
        views.quantityInput.requestFocus()
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
        addCoinDialogPresenter.onAddEntryRequested(
            coinName,
            exchange,
            quantity,
            price,
            fees,
            date,
            notes,
            typeId
        )
    }

    override fun didAddTrackerEntry() {
        hideKeyboard(views.selectedAsset.windowToken)
        dismiss()
    }
}
