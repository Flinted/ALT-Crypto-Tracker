package did.chris.alt.ui.constraintui.trackerSummary

import did.chris.alt.base.BasePresenter
import did.chris.alt.data.dataController.DataController
import rx.Subscription
import javax.inject.Inject

class PortfolioSummaryPresenter @Inject constructor(private val dataController: DataController) :
    BasePresenter<PortfolioContractView>(),
    PortfolioContractPresenter {

    // Properties
    private var summarySubscription: Subscription? = null
    private var updateSubscription: Subscription? = null
    private var errorSubscription: Subscription? = null

    // Lifecycle

    override fun initialise() {
        initialiseSummarySubscriber()
        initialiseUpdateSubscriber()
        initialiseErrorSubscriber()
        view?.setFABOnClickListener()
    }

    override fun onDestroy() {
        this.summarySubscription?.unsubscribe()
        this.updateSubscription?.unsubscribe()
        this.updateSubscription = null
        this.summarySubscription = null
    }

    // Private Functions
    private fun initialiseSummarySubscriber() {
        val subscription = dataController.summaryRefreshSubscriber()
        this.summarySubscription = subscription.first.subscribe {summary ->
            view?.updateForSummary(summary)
        }
        view?.updateForSummary(subscription.second)
    }

    private fun initialiseErrorSubscriber() {
        errorSubscription = dataController.getErrorSubscription().subscribe {
            view?.hideLoading()
        }
    }

    private fun initialiseUpdateSubscriber() {
        this.updateSubscription = dataController.updatingSubscriber().subscribe{
            view?.showLoading()
        }
    }
}
