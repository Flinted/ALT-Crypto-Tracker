package makes.flint.alt.ui.constraintui.addCoin

import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.factories.TrackerEntryDataFactory
import rx.Subscription
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

/**
 * AddCoinPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class AddCoinPresenter @Inject constructor(private val dataController: DataController,
                                           private val trackerEntryDataFactory: TrackerEntryDataFactory
) : AddCoinContractPresenter {

    // Properties

    private var dialog: AddCoinContractView? = null
    private var selectedCoin: CoinListItem? = null
    private var coinListSubscriber: Subscription? = null

    // Lifecycle

    override fun initialise() {
        initialiseCoinListSubscriber()
        dialog?.initialiseFABListener()
        dialog?.initialiseInputListeners()
        dialog?.initialiseDateSelectListener()
    }

    override fun attachView(view: AddCoinContractView) {
        this.dialog = view
    }

    override fun detachView() {
        this.dialog = null
    }

    // Overrides

    override fun updateSelectedCoin(coin: CoinListItem?) {
        this.selectedCoin = coin
    }

    override fun onAddEntryRequested(coinName: String?, exchange: String?, quantity: String?, price: String?, fees: String?, date: String, notes: String, typeId: String) {
        selectedCoin ?: let {
            dialog?.showError(ErrorHandler.ADD_TRANSACTION_FAILURE)
            return
        }
        val trackerEntry = dataController.getCopyOfTrackerEntry(selectedCoin?.name, selectedCoin?.symbol)
        val preparedEntry = trackerEntryDataFactory.makeTrackerEntryDataFor(trackerEntry,
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
            dialog?.showError(ErrorHandler.ADD_TRANSACTION_FAILURE)
            return
        }
        dataController.storeTrackerEntry(preparedEntry)
    }

    override fun prepareDateSelected(day: Int, month: Int, year: Int) {
        val day2digit = if (day < 10) "0$day" else day.toString()
        val month2digit = if (month < 10) "0$month" else month.toString()
        val year4digit = year.toString()
        val dateString = "$day2digit/$month2digit/$year4digit"
        dialog?.setDateSelected(dateString)
    }

    override fun updatePriceCalculation(quantity: String, price: String, fees: String) {
        if (quantity.isBlank() || quantity == ".") {
            dialog?.displayUpdatedPurchasePrice("...")
            dialog?.displayUpdatedCurrentPrice("...")
            return
        }
        val bigDecimalQuantity = BigDecimal(quantity)
        val bigDecimalFees = if (fees.isBlank()) BigDecimal.ZERO else BigDecimal(fees)
        updatePurchasePrice(bigDecimalQuantity, price, bigDecimalFees)
        updateCurrentPrice(bigDecimalQuantity)
    }

    // Private Functions

    private fun initialiseCoinListSubscriber() {
        val subscription = dataController.coinRefreshSubscriber()
        this.coinListSubscriber = subscription.first.subscribe {
            dialog?.initialiseCoinAutoSuggest(it)
        }
        dialog?.initialiseCoinAutoSuggest(subscription.second)
    }

    private fun updateCurrentPrice(bigDecimalQuantity: BigDecimal) {
        selectedCoin ?: return
        val coinPrice = selectedCoin?.priceData?.priceUSD ?: return
        val currentPrice = calculateAndRound(bigDecimalQuantity, coinPrice, BigDecimal.ZERO)
        dialog?.displayUpdatedCurrentPrice(currentPrice)
    }

    private fun updatePurchasePrice(bigDecimalQuantity: BigDecimal,
                                    price: String,
                                    bigDecimalFees: BigDecimal) {
        if (price.isBlank() || price == ".") {
            dialog?.displayUpdatedPurchasePrice("...")
            return
        }
        val bigDecimalPrice = BigDecimal(price)
        val purchasePrice = calculateAndRound(bigDecimalQuantity, bigDecimalPrice, bigDecimalFees)
        dialog?.displayUpdatedPurchasePrice(purchasePrice)
    }

    private fun calculateAndRound(bigDecimalQuantity: BigDecimal,
                                  bigDecimalPrice: BigDecimal,
                                  bigDecimalFees: BigDecimal): String {
        val calculatedTotal = bigDecimalPrice.multiply(bigDecimalQuantity).plus(bigDecimalFees)
        val rounded = calculatedTotal.setScale(2, RoundingMode.HALF_EVEN)
        return "$${rounded.toPlainString()}"
    }
}
