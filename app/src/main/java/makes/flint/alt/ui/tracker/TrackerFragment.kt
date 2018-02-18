package makes.flint.alt.ui.tracker

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.ui.interfaces.FilterView
import makes.flint.alt.ui.main.MainActivity
import makes.flint.alt.ui.tracker.addCoinDialog.AddCoinDialog
import makes.flint.alt.ui.tracker.summary.SummaryViewPager
import makes.flint.alt.ui.tracker.summary.SummaryViewPagerAdapter
import makes.flint.alt.ui.tracker.trackerEntryDialog.TrackerEntryDialog
import makes.flint.alt.ui.tracker.trackerList.TrackerAdapterContractView
import makes.flint.alt.ui.tracker.trackerList.TrackerListAdapter
import rx.functions.Action1

/**
 * TrackerFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
const val CHART_HIDDEN = 0
const val CHART_FULL_SCREEN = 1
const val CHART_OFF_SCREEN = 2

class TrackerFragment : BaseFragment(), TrackerContractView, FilterView {

    // View Bindings
    private lateinit var progressSpinner: ProgressBar
    private lateinit var trackerConstraint: ConstraintLayout
    private lateinit var trackerRecycler: RecyclerView
    private lateinit var summaryViewPager: SummaryViewPager
    private lateinit var addCoinButton: FloatingActionButton
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var noEntriesPlaceHolder: ConstraintLayout
    private lateinit var changeChartButton: ImageView
    private lateinit var cancelChartButton: ImageView
    private lateinit var hideSummaryAreaButton: ImageView
    private lateinit var showSummaryAreaButton: ImageView

    // Properties
    private val originalConstraint = ConstraintSet()
    private val noChartConstraint = ConstraintSet()
    private val chartViewConstraint = ConstraintSet()

    private lateinit var trackerPresenter: TrackerContractPresenter
    private lateinit var trackerListAdapter: TrackerAdapterContractView
    private lateinit var summaryPagerAdapter: SummaryViewPagerAdapter

    // Lifecycle
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_tracker, container, false)
        trackerPresenter = getPresenterComponent().provideTrackerPresenter()
        trackerPresenter.attachView(this)
        attachPresenter(trackerPresenter)
        bindViews(view)
        trackerPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        trackerListAdapter.onDestroy()
        trackerPresenter.onDestroy()
    }

    private fun bindViews(view: View?) {
        view ?: return
        this.progressSpinner = view.findViewById(R.id.tracker_progress_spinner)
        this.trackerRecycler = view.findViewById(R.id.tracker_recycler_view)
        this.addCoinButton = view.findViewById(R.id.tracker_FAB)
        this.changeChartButton = view.findViewById(R.id.tracker_show_chart_button)
        this.summaryViewPager = view.findViewById(R.id.tracker_chart_area)
        this.hideSummaryAreaButton = view.findViewById(R.id.tracker_hide_summary_area_button)
        this.showSummaryAreaButton = view.findViewById(R.id.tracker_show_summary_area_button)
        this.trackerConstraint = view.findViewById(R.id.tracker_main_content)
        this.swipeRefresh = view.findViewById(R.id.tracker_refresh_layout)
        this.cancelChartButton = view.findViewById(R.id.tracker_show_portfolio_button)
        this.noEntriesPlaceHolder = view.findViewById(R.id.tracker_no_entries_placeholder)
        initialiseConstraintSets()
    }

    // Initialisation
    override fun initialiseConstraintSets() {
        originalConstraint.clone(trackerConstraint)
        noChartConstraint.clone(trackerConstraint)
        chartViewConstraint.clone(trackerConstraint)
        configureNoChartConstraint()
        configureChartViewConstraint()
    }

    override fun initialiseSummaryPager() {
        summaryPagerAdapter = SummaryViewPagerAdapter(activity.supportFragmentManager)
        summaryViewPager.adapter = summaryPagerAdapter
    }

    override fun initialiseTrackerList() {
        trackerListAdapter = TrackerListAdapter(getPresenterComponent())
        trackerRecycler.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        trackerRecycler.adapter = trackerListAdapter as TrackerListAdapter
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
        swipeRefresh.setColorSchemeColors(refreshColour)
        swipeRefresh.setOnRefreshListener {
            (activity as MainActivity).clearSearchTerms()
            trackerPresenter.refreshCache()
        }
    }

    override fun initialiseAddCoinButtonListener() {
        addCoinButton.setOnClickListener {
            showAddCoinDialog()
        }
    }

    override fun initialiseChartListeners() {
        changeChartButton.setOnClickListener {
            changeChartVisibility(CHART_FULL_SCREEN)
        }
        hideSummaryAreaButton.setOnClickListener {
            changeChartVisibility(CHART_HIDDEN)
        }
        showSummaryAreaButton.setOnClickListener {
            changeChartVisibility(CHART_OFF_SCREEN)
        }
        cancelChartButton.setOnClickListener {
            changeChartVisibility(CHART_OFF_SCREEN)
        }
    }

    override fun hideProgressSpinner() {
        this.progressSpinner.visibility = View.GONE
        this.swipeRefresh.visibility = View.VISIBLE
    }

    private fun configureNoChartConstraint() {
        noChartConstraint.setVisibility(R.id.tracker_chart_area, View.GONE)
        noChartConstraint.setVisibility(R.id.tracker_hide_summary_area_button, View.GONE)
        noChartConstraint.setVisibility(R.id.tracker_show_summary_area_button, View.VISIBLE)
        noChartConstraint.connect(R.id.tracker_recycler_view, ConstraintSet.TOP, R.id.tracker_show_summary_area_button,
                ConstraintSet.BOTTOM)
    }

    private fun configureChartViewConstraint() {
        chartViewConstraint.connect(
                R.id.tracker_chart_area,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM)
        chartViewConstraint.constrainHeight(R.id.tracker_chart_area, ViewGroup.LayoutParams.MATCH_PARENT)
        chartViewConstraint.setVisibility(R.id.tracker_show_chart_button, View.GONE)
        chartViewConstraint.setVisibility(R.id.tracker_show_portfolio_button, View.VISIBLE)
    }

    private fun hideNoTrackerEntriesMessage() {
        noEntriesPlaceHolder.visibility = View.GONE
        swipeRefresh.visibility = View.VISIBLE
    }

    override fun showNoTrackerEntriesMessage() {
        swipeRefresh.visibility = View.GONE
        noEntriesPlaceHolder.visibility = View.VISIBLE
    }

    private fun makeTrackerEntryDialogFor(item: TrackerListItem) {
        val fragmentManager = activity.fragmentManager
        val shownCoinDetail = fragmentManager.findFragmentByTag("TrackerEntryDetail")
        shownCoinDetail?.let {
            fragmentManager.beginTransaction().remove(it).commit()
        }
        val newCoinDetail = TrackerEntryDialog.getInstanceFor(item)
        newCoinDetail.show(fragmentManager, "TrackerEntryDetail")
    }

    override fun filterFor(input: String) {
        trackerListAdapter.filterFor(input)
    }

    private fun changeChartVisibility(stateId: Int) {
        val constraintSet = when (stateId) {
            CHART_FULL_SCREEN -> chartViewConstraint
            CHART_HIDDEN -> noChartConstraint
            else -> originalConstraint
        }
        val refreshable = stateId != CHART_FULL_SCREEN
        val currentItem = if (stateId == CHART_FULL_SCREEN) 1 else 0
        swipeRefresh.isEnabled = refreshable
        summaryViewPager.setCurrentItem(currentItem, true)
        TransitionManager.beginDelayedTransition(trackerConstraint)
        constraintSet.applyTo(trackerConstraint)
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    // Add Coin Dialog
    private fun showAddCoinDialog() {
        val fragmentManager = activity.fragmentManager
        val shownAddCoin = fragmentManager.findFragmentByTag("AddCoin")
        shownAddCoin?.let {
            fragmentManager.beginTransaction().remove(it).commit()
        }
        val addCoinDialog = AddCoinDialog()
        addCoinDialog.show(fragmentManager, "AddCoin")
        setListenerForAddCoinDialog(addCoinDialog)
    }

    private fun setListenerForAddCoinDialog(addCoinDialog: AddCoinDialog) {
        addCoinDialog.onTransactionAdded().subscribe {
            if (!it) {
                return@subscribe
            }
            trackerPresenter.refreshTrackerEntries()
        }
    }
}
