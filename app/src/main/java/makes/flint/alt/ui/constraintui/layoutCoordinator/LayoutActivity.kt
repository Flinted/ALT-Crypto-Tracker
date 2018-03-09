package makes.flint.alt.ui.constraintui.layoutCoordinator

import android.os.Bundle
import android.view.ViewTreeObserver
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.ui.constraintui.coinlist.CoinListFragment
import makes.flint.alt.ui.constraintui.trackerChart.TrackerChartFragment
import makes.flint.alt.ui.tracker.summary.summaryFragments.SummaryFragment

/**
 * LayoutActivity
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class LayoutActivity : BaseActivity(), LayoutActivityContractView, LayoutCoordinatable {

    private lateinit var views: LayoutViewHolder
    private lateinit var presenter: LayoutActivityContractPresenter
    private lateinit var coordinator: LayoutCoordinator
    private var currentViewState = loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template)
        this.views = LayoutViewHolder(this)
        setOnClick()
        presenter = getPresenterComponent().provideLayoutPresenter()
        presenter.attachView(this)
        attachPresenter(presenter)
        coordinator = LayoutCoordinator()
        coordinator.initialiseConstraintsFor(views.masterLayout)
        presenter.initialise()
    }

    override fun loadInitialScreens() {
        val coinListFragment = CoinListFragment()
        val chartFragment = TrackerChartFragment()
        val summaryFragment = SummaryFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_c, coinListFragment)
        transaction.replace(R.id.frame_a, summaryFragment)
        transaction.replace(R.id.frame_b, chartFragment)
        transaction.commit()
        views.masterLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver
        .OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                views.masterLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                presenter.emitData()
                views.fab.callOnClick()
            }
        })
    }

    override fun updateLayout(key: String) {
        currentViewState = key
        coordinator.changeConstraints(key, views.masterLayout)
        coordinator.updateFragments(key, views)
    }

    private fun setOnClick() {
        views.fab.setOnClickListener {
            val viewKey = when (currentViewState) {
                loading -> home
                home -> coin
                else -> home
            }
            updateLayout(viewKey)
        }
    }

    override fun displayError(it: Throwable) {
        ErrorHandler.showError(this, ErrorHandler.ERROR_SYNC_TIMEOUT)
    }

    override fun onBackPressed() {
        val viewKey = when (currentViewState) {
            coin -> home
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
