package makes.flint.poh.coinlist

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.response.coinSummary.SummaryCoinResponse

/**
 * CoinListAdapterContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface CoinListAdapterContractView: BaseContractView {
    var coinList: MutableList<SummaryCoinResponse>
}

interface CoinListAdapterContractPresenter: BaseContractPresenter<BaseContractView> {
}