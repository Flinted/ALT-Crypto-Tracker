package makes.flint.poh.ui.tracker.addCoinDialog

import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.errors.ErrorHandler
import makes.flint.poh.factories.TrackerEntryDataFactory
import rx.Subscription
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

/**
 * AddCoinDialogPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class AddCoinDialogPresenter @Inject constructor(private val dataController: DataController,
                                                 private val trackerEntryDataFactory: TrackerEntryDataFactory
) : AddCoinDialogContractPresenter {

    private var dialog: AddCoinDialogContractView? = null
    private var selectedCoin: CoinListItem? = null

    private var coinListSubscriber: Subscription? = null

    override fun attachView(view: AddCoinDialogContractView) {
        this.dialog = view
    }

    override fun detachView() {
        this.dialog = null
    }

    override fun initialise() {
        initialiseCoinListSubscriber()
        dialog?.initialiseFABListener()
        dialog?.initialiseInputListeners()
        dialog?.initialiseDateSelectListener()
        dataController.refreshRequested()
    }

    private fun initialiseCoinListSubscriber() {
        this.coinListSubscriber = dataController.coinRefreshSubscriber().subscribe {
            dialog?.initialiseCoinAutoSuggest(it)
        }
    }

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
        dialog?.endDialog()
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
