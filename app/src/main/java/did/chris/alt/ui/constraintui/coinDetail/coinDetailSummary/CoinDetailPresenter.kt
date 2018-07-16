package did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
import javax.inject.Inject

class CoinDetailPresenter @Inject constructor(private var dataController: DataController)
    : BasePresenter<CoinDetailContractView>(), CoinDetailContractPresenter {

    // Properties
    private lateinit var coinSymbol: String

    // Overrides
    override fun setCoinSymbol(coinSymbol: String?) {
        coinSymbol ?: return
        this.coinSymbol = coinSymbol
    }
    override fun initialise() {
        val coin = dataController.getCoinForSymbol(coinSymbol) ?: return
        view?.displayCoinDetail(coin)
        view?.initialiseDYORButton(coin.symbol)
        view?.initialiseAddEntryButton(coin.searchKey)
    }
}
