package did.chris.alt.ui.constraintui.layoutCoordinator

import android.os.Bundle
import android.view.ViewTreeObserver
import did.chris.alt.R
import did.chris.alt.base.BaseActivity
import did.chris.alt.errors.ErrorHandler
import did.chris.alt.layoutCoordination.*
import did.chris.alt.layoutCoordination.viewTransitions.ViewStateTransition
import did.chris.alt.ui.constraintui.coinlist.CoinListFragment
import did.chris.alt.ui.constraintui.trackerBarChart.TrackerBarChartFragment
import did.chris.alt.ui.constraintui.trackerSummary.PortfolioSummaryFragment
import java.util.*

/**
 * LayoutActivity
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class LayoutActivity : BaseActivity(), LayoutActivityContractView, LayoutCoordinatable {

    private lateinit var views: LayoutViewHolder
    private lateinit var presenter: LayoutActivityContractPresenter
    private lateinit var coordinator: LayoutCoordinator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template)
        this.views = LayoutViewHolder(this)
        presenter = getPresenterComponent().provideLayoutPresenter()
        presenter.attachView(this)
        attachPresenter(presenter)
        coordinator = LayoutCoordinator(this)
        presenter.initialise()
    }

    override fun onResume() {
        super.onResume()
        showLoading()
        presenter.emitData()
    }

    override fun loadInitialScreens() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_bottom, CoinListFragment())
        transaction.replace(R.id.frame_centre, PortfolioSummaryFragment())
        transaction.replace(R.id.frame_top, TrackerBarChartFragment())
        transaction.commit()
        views.masterLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver
        .OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                views.masterLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                presenter.emitData()
                views.loadingMessage.text = ""
                updateLayout(home)
            }
        })
    }

    override fun displayRandomLoadingMessage() {
        val stringArray = resources.getStringArray(R.array.loading_messages)
        val randomPosition = Random().nextInt(stringArray.count())
        val string = stringArray[randomPosition]
        views.loadingMessage.text = string
    }

    override fun updateLayout(key: String, viewStateTransition: ViewStateTransition?) {
        coordinator.changeConstraints(
            key,
            views.masterLayout,
            supportFragmentManager,
            viewStateTransition
        )
    }

    override fun displayError(it: Throwable) {
        ErrorHandler.showError(this, ErrorHandler.API_FAILURE)
    }

    override fun onBackPressed() {
        val currentViewState = coordinator.currentViewState
        val viewKey = when (currentViewState) {
            coin, coinToSearch, coinToTracker -> home
            trackerToSearch                   -> searchToTracker
            coinToAddCoin                     -> coinToTracker
            else                              -> null
        }
        viewKey?.let {
            updateLayout(viewKey)
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
