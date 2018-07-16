package did.chris.alt.ui.constraintui.addCoin

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.coinListItem.CoinListItem
import rx.Observable

interface AddCoinContractView : BaseContractView {

    fun initialiseFABListener()
    fun initialiseInputListeners()
    fun displayUpdatedPurchasePrice(purchasePrice: String)
    fun onTransactionAdded(): Observable<Boolean>?
    fun initialiseCoinAutoSuggest(autoCompleteSuggestions: List<CoinListItem>)
    fun displayUpdatedCurrentValue(currentPrice: String)
    fun initialiseDateSelectListener()
    fun setDateSelected(dateString: String)
    fun didAddTrackerEntry()
    fun displayCurrentAssetPrice(coinPrice: String)
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
