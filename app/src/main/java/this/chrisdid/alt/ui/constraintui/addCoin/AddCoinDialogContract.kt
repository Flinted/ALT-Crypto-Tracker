package this.chrisdid.alt.ui.constraintui.addCoin

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView
import this.chrisdid.alt.data.coinListItem.CoinListItem
import rx.Observable

/**
 * AddCoinDialogContract
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
interface AddCoinContractView : BaseContractView {
    fun initialiseFABListener()
    fun initialiseInputListeners()
    fun displayUpdatedPurchasePrice(purchasePrice: String)
    fun onTransactionAdded(): Observable<Boolean>?
    fun initialiseCoinAutoSuggest(autoCompleteSuggestions: List<CoinListItem>)
    fun displayUpdatedCurrentPrice(currentPrice: String)
    fun initialiseDateSelectListener()
    fun setDateSelected(dateString: String)
    fun didAddTrackerEntry()
}

interface AddCoinContractPresenter : BaseContractPresenter<AddCoinContractView> {
    fun onAddEntryRequested(coinName: String?,
                            exchange: String?,
                            quantity: String?,
                            price: String?,
                            fees: String?,
                            date: String,
                            notes: String,
                            typeId: String)
    fun updatePriceCalculation(quantity: String, price: String, fees: String)
    fun updateSelectedCoin(coin: CoinListItem?)
    fun prepareDateSelected(day: Int, month: Int, year: Int)
}
