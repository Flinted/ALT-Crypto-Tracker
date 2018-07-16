package did.chris.alt.ui.constraintui.coinlist.coinListAdapter

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.coinListItem.CoinListItem
import rx.Observable

interface CoinListAdapterContractView : BaseContractView {

    // Properties
    var coinList: MutableList<CoinListItem>

    // Functions
    fun filterFor(input: String)
    fun itemChangedAt(position: Int)
    fun onCoinSelected(): Observable<String>
    fun onDestroy()
    fun onSortTypeChanged(): Observable<Int>
    fun emitSortTypeChanged(sortId: Int)
    fun updateIconPack()
    fun isFiltered(): Boolean
}

interface CoinListAdapterContractPresenter : BaseContractPresenter<BaseContractView> {

    // Functions
    fun onFavouriteStateChanged(isFavourite: Boolean, coin: CoinListItem, position: Int)
    fun onDestroy()
}