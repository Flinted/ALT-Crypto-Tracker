package did.chris.alt.ui.constraintui.addCoin

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.coinListItem.CoinListItem
import did.chris.alt.data.dataController.DataController
import did.chris.alt.errors.ErrorHandler
import did.chris.alt.factories.TrackerEntryDataFactory
import did.chris.alt.utility.NumberFormatter
import rx.Subscription
import java.math.BigDecimal
import javax.inject.Inject

class AddCoinPresenter @Inject constructor(
    private val dataController: DataController,
    private val trackerEntryDataFactory: TrackerEntryDataFactory
) : BasePresenter<AddCoinContractView>(), AddCoinContractPresenter {

    // Properties
    private var selectedCoin: CoinListItem? = null
    private var coinListSubscriber: Subscription? = null

    // Lifecycle
    override fun initialise() {
        setPlaceholdersForValues()
        initialiseCoinListSubscriber()
        view?.initialiseFABListener()
        view?.initialiseInputListeners()
        view?.initialiseDateSelectListener()
    }

    private fun setPlaceholdersForValues() {
        val placeholder = "..."
        view?.displayCurrentAssetPrice(placeholder)
        view?.displayUpdatedCurrentValue(placeholder)
        view?.displayUpdatedPurchasePrice(placeholder)
    }

    // Overrides
    override fun updateSelectedCoin(coin: CoinListItem?) {
        this.selectedCoin = coin
    }

    override fun onAddEntryRequested(
        coinName: String?,
        exchange: String?,
        quantity: String?,
        price: String?,
        fees: String?,
        date: String,
        notes: String,
        typeId: String
    ) {
        selectedCoin ?: let {
            view?.showError(ErrorHandler.ADD_TRANSACTION_FAILURE)
            return
        }
        val trackerEntry =
            dataController.getCopyOfTrackerEntry(selectedCoin?.name, selectedCoin?.symbol)
        val preparedEntry = trackerEntryDataFactory.makeTrackerEntryDataFor(
            trackerEntry,
            selectedCoin,
            exchange,
            quantity,
            price,
            fees,
            date,
            notes,
            typeId
        )
        preparedEntry ?: let {
            view?.showError(ErrorHandler.ADD_TRANSACTION_FAILURE)
            return
        }
        dataController.storeTrackerEntry(preparedEntry)
        view?.didAddTrackerEntry()
    }

    override fun prepareDateSelected(day: Int, month: Int, year: Int) {
        val day2digit = if (day < 10) "0$day" else day.toString()
        val month2digit = if (month < 10) "0$month" else month.toString()
        val year4digit = year.toString()
        val dateString = "$day2digit/$month2digit/$year4digit"
        view?.setDateSelected(dateString)
    }

    override fun updatePriceCalculation(quantity: String, price: String, fees: String) {
        if (quantity.isBlank() || quantity == ".") {
            view?.displayUpdatedPurchasePrice("...")
            view?.displayUpdatedCurrentValue("...")
            updateCurrentCost()
            return
        }
        val bigDecimalQuantity = BigDecimal(quantity)
        val bigDecimalFees = if (fees.isBlank()) BigDecimal.ZERO else BigDecimal(fees)
        updatePurchasePrice(bigDecimalQuantity, price, bigDecimalFees)
        updateCurrentPrice(bigDecimalQuantity)
        updateCurrentCost()
    }

    // Private Functions
    private fun initialiseCoinListSubscriber() {
        val subscription = dataController.coinRefreshSubscriber()
        this.coinListSubscriber = subscription.first.subscribe {
            view?.initialiseCoinAutoSuggest(it)
        }
        view?.initialiseCoinAutoSuggest(subscription.second)
    }

    private fun updateCurrentPrice(bigDecimalQuantity: BigDecimal) {
        selectedCoin ?: return
        val coinPrice = selectedCoin?.priceData?.priceUSD ?: return
        val currentPrice = calculateAndRound(bigDecimalQuantity, coinPrice, BigDecimal.ZERO)
        view?.displayUpdatedCurrentValue(currentPrice)
    }

    private fun updateCurrentCost() {
        val coinPrice = selectedCoin?.priceData?.priceUSD ?: return
        val formattedPrice = NumberFormatter.formatCurrencyAutomaticDigit(coinPrice)
        view?.displayCurrentAssetPrice(formattedPrice)

    }

    private fun updatePurchasePrice(
        bigDecimalQuantity: BigDecimal,
        price: String,
        bigDecimalFees: BigDecimal
    ) {
        if (price.isBlank() || price == ".") {
            view?.displayUpdatedPurchasePrice("...")
            return
        }
        val bigDecimalPrice = BigDecimal(price)
        val purchasePrice = calculateAndRound(bigDecimalQuantity, bigDecimalPrice, bigDecimalFees)
        view?.displayUpdatedPurchasePrice(purchasePrice)
    }

    private fun calculateAndRound(
        bigDecimalQuantity: BigDecimal,
        bigDecimalPrice: BigDecimal,
        bigDecimalFees: BigDecimal
    ): String {
        val calculatedTotal = bigDecimalPrice.multiply(bigDecimalQuantity).plus(bigDecimalFees)
        return NumberFormatter.formatCurrency(calculatedTotal, 2)
    }
}
