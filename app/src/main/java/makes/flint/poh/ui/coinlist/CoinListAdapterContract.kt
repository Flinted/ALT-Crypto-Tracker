package makes.flint.poh.ui.coinlist

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
    fun refreshList()
    fun filterFor(input: String)
    fun updateLastSync(lastSync: String)
    fun notRefreshed()
    fun onSyncCompleted(): Observable<String?>
    fun itemChangedAt(position: Int)
}

interface CoinListAdapterContractPresenter : BaseContractPresenter<BaseContractView> {
}