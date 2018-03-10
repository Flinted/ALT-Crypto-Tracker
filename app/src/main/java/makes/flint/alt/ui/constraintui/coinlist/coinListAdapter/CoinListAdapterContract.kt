package makes.flint.alt.ui.constraintui.coinlist.coinListAdapter

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.coinListItem.CoinListItem
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
}

interface CoinListAdapterContractPresenter : BaseContractPresenter<BaseContractView> {
    fun onFavouriteStateChanged(isFavourite: Boolean, coin: CoinListItem, position: Int)
    fun onDestroy()
}