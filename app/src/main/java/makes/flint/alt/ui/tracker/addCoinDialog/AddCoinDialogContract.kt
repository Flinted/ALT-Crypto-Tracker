package makes.flint.alt.ui.tracker.addCoinDialog

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.coinListItem.CoinListItem
import rx.Observable

/**
 * AddCoinDialogContract
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
interface AddCoinDialogContractView : BaseContractView {
    fun initialiseFABListener()
    fun initialiseInputListeners()
    fun displayUpdatedPurchasePrice(purchasePrice: String)
    fun endDialog()
    fun onTransactionAdded(): Observable<Boolean>?
    fun initialiseCoinAutoSuggest(autoCompleteSuggestions: List<CoinListItem>)
    fun displayUpdatedCurrentPrice(currentPrice: String)
    fun initialiseDateSelectListener()
    fun setDateSelected(dateString: String)
}

interface AddCoinDialogContractPresenter : BaseContractPresenter<AddCoinDialogContractView> {
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
