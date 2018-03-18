package makes.flint.alt.ui.constraintui.layoutCoordinator

import android.os.Bundle
import android.view.ViewTreeObserver
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.layoutCoordination.*
import makes.flint.alt.layoutCoordination.viewTransitions.ViewStateTransition
import makes.flint.alt.ui.constraintui.coinlist.CoinListFragment
import makes.flint.alt.ui.constraintui.trackerChart.TrackerChartFragment
import makes.flint.alt.ui.constraintui.trackerSummary.SummaryFragment
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
        transaction.replace(R.id.frame_top, SummaryFragment())
        transaction.replace(R.id.frame_centre, TrackerChartFragment())
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
        println("key $key and view: $viewStateTransition")
        coordinator.changeConstraints(key, views.masterLayout, supportFragmentManager, viewStateTransition)
    }

    override fun displayError(it: Throwable) {
        ErrorHandler.showError(this, ErrorHandler.ERROR_SYNC_TIMEOUT)
    }

    override fun onBackPressed() {
        val viewKey = when (coordinator.currentViewState) {
            coin, search, tracker -> home
            addCoin -> tracker
            else -> null
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
