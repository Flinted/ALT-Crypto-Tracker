package did.chris.alt.ui.constraintui.coinlist

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

class CoinListPresenter @Inject constructor(private val dataController: DataController) :
    BasePresenter<CoinListContractView>(), CoinListContractPresenter {

    // Properties
    private var timeStampSubscription: Subscription? = null
    private var updateSubscription: Subscription? = null
    private var marketSubscription: Subscription? = null
    private var errorSubscription: Subscription? = null

    // Overrides
    override fun initialise() {
        subscribeForRefreshUpdates()
        view?.initialiseSearchOnClick()
        view?.initialiseListAdapter()
        view?.initialiseSwipeRefreshListener()
        view?.initialiseScrollListener()
        view?.initialiseFABonClick()
    }

    override fun assessScrollChange(yPosition: Int) {
        if (yPosition < 2500) {
            view?.hideGoToTopFAB()
            return
        }
        view?.showGoToTopFAB()
    }

    // Private Functions
    private fun subscribeForRefreshUpdates() {
        val subscription = dataController.lastSyncSubscriber()
        timeStampSubscription = subscription.first.subscribe {
            view?.hideLoading()
        }
        updateSubscription = dataController.updatingSubscriber().subscribe {
            view?.showLoading()
        }
        val marketRefreshSubscription = dataController.marketRefreshSubscriber()
        view?.displayMarketSummary(marketRefreshSubscription.second)
        marketSubscription = marketRefreshSubscription.first.subscribe {
            view?.displayMarketSummary(it)
        }
        errorSubscription = dataController.getErrorSubscription().subscribe {
            view?.hideLoading()
        }
    }

    override fun refresh() {
        dataController.refreshRequested()
    }

    override fun onCoinSelected(coinSymbol: String) {
        view?.showDialogForCoin(coinSymbol)
    }

    override fun onDestroy() {
        detachView()
        timeStampSubscription?.unsubscribe()
        updateSubscription?.unsubscribe()
        marketSubscription?.unsubscribe()
        errorSubscription?.unsubscribe()
        timeStampSubscription = null
        updateSubscription = null
        marketSubscription = null
        errorSubscription = null
    }
}