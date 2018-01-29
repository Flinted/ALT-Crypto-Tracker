package makes.flint.poh.ui.coinlist

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.coinListItem.CoinListItem

/**
 * CoinListAdapterContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface CoinListAdapterContractView: BaseContractView {
    var coinList: MutableList<CoinListItem>
    fun refreshList()
    fun filterFor(input: String)
}

interface CoinListAdapterContractPresenter: BaseContractPresenter<BaseContractView> {
}