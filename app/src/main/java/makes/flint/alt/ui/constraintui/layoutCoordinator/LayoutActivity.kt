package makes.flint.alt.ui.constraintui.layoutCoordinator

import android.app.FragmentTransaction
import android.os.Bundle
import android.view.ViewTreeObserver
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.layoutCoordination.*
import makes.flint.alt.layoutCoordination.viewTransitions.HomeTransition
import makes.flint.alt.ui.constraintui.addCoin.AddCoinFragment
import makes.flint.alt.ui.constraintui.coinlist.CoinListFragment
import makes.flint.alt.ui.constraintui.summaryChart.SummaryChartFragment
import makes.flint.alt.ui.constraintui.trackerChart.TrackerChartFragment
import makes.flint.alt.ui.constraintui.trackerList.TrackerListFragment
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
        coordinator = LayoutCoordinator()
        coordinator.initialiseConstraintsFor(views.masterLayout)
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

    override fun updateLayout(key: String) {
        val callback = getCallbackForConstraintState(key)
        coordinator.changeConstraints(key, views.masterLayout, callback)
    }

    private fun getCallbackForConstraintState(key: String): TransitionCallBack? {
        return when (key) {
            addCoin -> prepareAddCoinState()
            tracker -> prepareTrackerState()
            else -> prepareHomeState()
        }
    }

    private fun prepareAddCoinState(): TransitionCallBack? {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pop_frame_bottom, AddCoinFragment())
        transaction.commit()
        return null
    }

    private fun prepareTrackerState(): TransitionCallBack? {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_centre, SummaryChartFragment())
        transaction.replace(R.id.pop_frame_bottom, TrackerListFragment())
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
        transaction.commit()
        return null
    }

    private fun prepareHomeState(): TransitionCallBack {
        val beforeState = coordinator.currentViewState
        val homeTransition = HomeTransition(this)
        return object : TransitionCallBack {
            override fun transitionCompleted() {
                if (beforeState == home) {
                    return
                }
                val transaction = supportFragmentManager.beginTransaction()
                homeTransition.postExecute(transaction, views.masterLayout)
//                transaction.replace(R.id.frame_centre, TrackerChartFragment())
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                views.popFrameBottom.removeAllViews()
//                views.popFrameTop.removeAllViews()
                transaction.commit()
            }
        }
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
