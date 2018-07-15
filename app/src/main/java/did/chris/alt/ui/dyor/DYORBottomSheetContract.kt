package did.chris.alt.ui.dyor

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.coinListItem.CoinListItem

/**
 * DYORBottomSheetContract
 */
interface DYORBottomSheetContractView : BaseContractView {
    fun initialiseGoogleButton(coin: CoinListItem?)
    fun initialiseFacebookButton(coin: CoinListItem?)
    fun initialiseGitHubButton(coin: CoinListItem?)
    fun initialiseCoinMarketCapButton(coin: CoinListItem?)
}

interface DYORBottomSheetContractPresenter : BaseContractPresenter<DYORBottomSheetContractView> {
    fun setCoinSymbol(coinSymbol: String?)
}
