package makes.flint.poh.ui.market.coinlist

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.coinListItem.CoinListItem
import rx.Observable

/**
 * CoinListAdapterContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface CoinListAdapterContractView : BaseContractView {
    var coinList: MutableList<CoinListItem>
    fun filterFor(input: String)
    fun itemChangedAt(position: Int)
    fun onCoinSelected(): Observable<String>
    fun onDestroy()
}

interface CoinListAdapterContractPresenter : BaseContractPresenter<BaseContractView> {
    fun onFavouriteStateChanged(isFavourite: Boolean, coin: CoinListItem, position: Int)
    fun onDestroy()
}