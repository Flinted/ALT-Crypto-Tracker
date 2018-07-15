package did.chris.alt.ui.dyor

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
import javax.inject.Inject

class DYORBottomSheetPresenter @Inject constructor(private val dataController: DataController) :
    BasePresenter<DYORBottomSheetContractView>(),
    DYORBottomSheetContractPresenter {

    private lateinit var coinSymbol: String

    override fun setCoinSymbol(coinSymbol: String?) {
        coinSymbol ?: return
        this.coinSymbol = coinSymbol
    }

    override fun initialise() {
        val coin = dataController.getCoinForSymbol(coinSymbol)
        view?.initialiseGoogleButton(coin)
        view?.initialiseFacebookButton(coin)
        view?.initialiseGitHubButton(coin)
        view?.initialiseCoinMarketCapButton(coin)
    }
}