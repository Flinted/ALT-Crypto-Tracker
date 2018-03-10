package makes.flint.alt.ui.tracker

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.ui.constraintui.addCoin.AddCoinFragment
import makes.flint.alt.ui.interfaces.FilterView
import makes.flint.alt.ui.main.MainActivity
import makes.flint.alt.ui.tracker.summary.SummaryViewPagerAdapter
import makes.flint.alt.ui.tracker.trackerEntryDialog.TrackerEntryDialog
import makes.flint.alt.ui.tracker.trackerList.TrackerAdapterContractView
import makes.flint.alt.ui.tracker.trackerList.TrackerListAdapter
import rx.functions.Action1

/**
 * TrackerFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
const val CHART_HIDDEN = 0
const val CHART_FULL_SCREEN = 1
const val CHART_OFF_SCREEN = 2

class TrackerFragment : BaseFragment(), TrackerContractView, FilterView {

    // Properties

    private lateinit var views: TrackerFragmentViewholder
    private lateinit var trackerPresenter: TrackerContractPresenter
    private lateinit var trackerListAdapter: TrackerAdapterContractView
    private lateinit var summaryPagerAdapter: SummaryViewPagerAdapter

    // Lifecycle

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_tracker, container, false)
        view ?: return null
        trackerPresenter = getPresenterComponent().provideTrackerPresenter()
        trackerPresenter.attachView(this)
        attachPresenter(trackerPresenter)
        this.views = TrackerFragmentViewholder(view)
        trackerPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        trackerListAdapter.onDestroy()
        trackerPresenter.onDestroy()
    }

    // Other Overrides

    override fun initialiseSummaryPager() {
        summaryPagerAdapter = SummaryViewPagerAdapter(activity.supportFragmentManager)
        views.summaryViewPager.adapter = summaryPagerAdapter
    }

    override fun initialiseTrackerList() {
        trackerListAdapter = TrackerListAdapter(getPresenterComponent())
        views.trackerRecycler.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        views.trackerRecycler.adapter = trackerListAdapter as TrackerListAdapter
    }

    override fun initialiseTrackerListListeners() {
        trackerListAdapter.onTrackerEntrySelected().subscribe {
            makeTrackerEntryDialogFor(it)
        }
        trackerListAdapter.onNoEntriesPresent().subscribe(Action1<Boolean> {
            if (!it) {
                hideNoTrackerEntriesMessage()
                return@Action1
            }
            showNoTrackerEntriesMessage()
        })
    }

    override fun initialiseRefreshListener() {
        val refreshColour = ContextCompat.getColor(context, R.color.colorAccent)
        views.swipeRefresh.setColorSchemeColors(refreshColour)
        views.swipeRefresh.setOnRefreshListener {
            (activity as MainActivity).clearSearchTerms()
            trackerPresenter.refreshCache()
        }
    }

    override fun initialiseAddCoinButtonListener() {
        views.addCoinButton.setOnClickListener {
            showAddCoinDialog()
        }
    }

    override fun initialiseChartListeners() {
        views.changeChartButton.setOnClickListener {
            changeChartVisibility(CHART_FULL_SCREEN)
        }
        views.hideSummaryAreaButton.setOnClickListener {
            changeChartVisibility(CHART_HIDDEN)
        }
        views.showSummaryAreaButton.setOnClickListener {
            changeChartVisibility(CHART_OFF_SCREEN)
        }
        views.cancelChartButton.setOnClickListener {
            changeChartVisibility(CHART_OFF_SCREEN)
        }
    }

    override fun initialiseShowSnapShotButtonListener() {
        views.snapShotButton.setOnClickListener {
        }
    }

    override fun hideProgressSpinner() {
        views.progressSpinner.visibility = View.GONE
        views.swipeRefresh.visibility = View.VISIBLE
    }

    override fun showNoTrackerEntriesMessage() {
        views.swipeRefresh.visibility = View.GONE
        views.noEntriesPlaceHolder.visibility = View.VISIBLE
    }

    override fun filterFor(input: String) {
        trackerListAdapter.filterFor(input)
    }

    override fun showLoading() {
        views.swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        views.swipeRefresh.isRefreshing = false
    }

    // Private Functions

    private fun makeTrackerEntryDialogFor(item: TrackerListItem) {
        val fragmentManager = activity.fragmentManager
        val shownCoinDetail = fragmentManager.findFragmentByTag("TrackerEntryDetail")
        shownCoinDetail?.let {
            fragmentManager.beginTransaction().remove(it).commit()
        }
        val newCoinDetail = TrackerEntryDialog.getInstanceFor(item)
        newCoinDetail.show(fragmentManager, "TrackerEntryDetail")
    }

    private fun hideNoTrackerEntriesMessage() {
        views.noEntriesPlaceHolder.visibility = View.GONE
        views.swipeRefresh.visibility = View.VISIBLE
    }

    private fun changeChartVisibility(stateId: Int) {
        val constraintSet = when (stateId) {
            CHART_FULL_SCREEN -> views.chartViewConstraint
            CHART_HIDDEN -> views.noChartConstraint
            else -> views.originalConstraint
        }
        val refreshable = stateId != CHART_FULL_SCREEN
        val currentItem = if (stateId == CHART_FULL_SCREEN) 1 else 0
        views.swipeRefresh.isEnabled = refreshable
        views.summaryViewPager.setCurrentItem(currentItem, true)
        TransitionManager.beginDelayedTransition(views.trackerConstraint)
        constraintSet.applyTo(views.trackerConstraint)
    }

    private fun showAddCoinDialog() {
        val fragmentManager = activity.fragmentManager
        val shownAddCoin = fragmentManager.findFragmentByTag("AddCoin")
        shownAddCoin?.let {
            fragmentManager.beginTransaction().remove(it).commit()
        }
        val addCoinDialog = AddCoinFragment()
        setListenerForAddCoinDialog(addCoinDialog)
    }

    private fun setListenerForAddCoinDialog(addCoinDialog: AddCoinFragment) {
        addCoinDialog.onTransactionAdded().subscribe {
            if (!it) {
                return@subscribe
            }
            trackerPresenter.refreshTrackerEntries()
        }
    }
}
