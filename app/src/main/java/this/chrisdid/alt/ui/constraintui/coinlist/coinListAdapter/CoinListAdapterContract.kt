package this.chrisdid.alt.ui.constraintui.coinlist.coinListAdapter

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView
import this.chrisdid.alt.data.coinListItem.CoinListItem
import rx.Observable

/**
 * CoinListAdapterContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface CoinListAdapterContractView : BaseContractView {
    var coinList: MutableList<CoinListItem>
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
    fun onFavouriteStateChanged(isFavourite: Boolean, coin: CoinListItem, position: Int)
    fun onDestroy()
}