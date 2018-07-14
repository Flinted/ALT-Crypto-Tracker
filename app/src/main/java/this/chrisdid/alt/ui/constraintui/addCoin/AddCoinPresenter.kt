package this.chrisdid.alt.ui.constraintui.addCoin

import this.chrisdid.alt.base.BasePresenter
import this.chrisdid.alt.data.coinListItem.CoinListItem
import this.chrisdid.alt.data.dataController.DataController
import this.chrisdid.alt.errors.ErrorHandler
import this.chrisdid.alt.factories.TrackerEntryDataFactory
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
) : BasePresenter<AddCoinContractView>(), AddCoinContractPresenter {

    // Properties

    private var selectedCoin: CoinListItem? = null
    private var coinListSubscriber: Subscription? = null

    // Lifecycle

    override fun initialise() {
        initialiseCoinListSubscriber()
        view?.initialiseFABListener()
        view?.initialiseInputListeners()
        view?.initialiseDateSelectListener()
    }

    // Overrides

    override fun updateSelectedCoin(coin: CoinListItem?) {
        this.selectedCoin = coin
    }

    override fun onAddEntryRequested(coinName: String?, exchange: String?, quantity: String?, price: String?, fees: String?, date: String, notes: String, typeId: String) {
        selectedCoin ?: let {
            view?.showError(ErrorHandler.ADD_TRANSACTION_FAILURE)
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
            view?.displayUpdatedCurrentPrice("...")
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
            view?.initialiseCoinAutoSuggest(it)
        }
        view?.initialiseCoinAutoSuggest(subscription.second)
    }

    private fun updateCurrentPrice(bigDecimalQuantity: BigDecimal) {
        selectedCoin ?: return
        val coinPrice = selectedCoin?.priceData?.priceUSD ?: return
        val currentPrice = calculateAndRound(bigDecimalQuantity, coinPrice, BigDecimal.ZERO)
        view?.displayUpdatedCurrentPrice(currentPrice)
    }

    private fun updatePurchasePrice(bigDecimalQuantity: BigDecimal,
                                    price: String,
                                    bigDecimalFees: BigDecimal) {
        if (price.isBlank() || price == ".") {
            view?.displayUpdatedPurchasePrice("...")
            return
        }
        val bigDecimalPrice = BigDecimal(price)
        val purchasePrice = calculateAndRound(bigDecimalQuantity, bigDecimalPrice, bigDecimalFees)
        view?.displayUpdatedPurchasePrice(purchasePrice)
    }

    private fun calculateAndRound(bigDecimalQuantity: BigDecimal,
                                  bigDecimalPrice: BigDecimal,
                                  bigDecimalFees: BigDecimal): String {
        val calculatedTotal = bigDecimalPrice.multiply(bigDecimalQuantity).plus(bigDecimalFees)
        val rounded = calculatedTotal.setScale(2, RoundingMode.HALF_EVEN)
        return "$${rounded.toPlainString()}"
    }
}
