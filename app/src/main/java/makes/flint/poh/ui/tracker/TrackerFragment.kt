package makes.flint.poh.ui.tracker

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
import android.widget.TextView
import makes.flint.poh.R
import makes.flint.poh.base.BaseFragment
import makes.flint.poh.data.Summary
import makes.flint.poh.data.trackerListItem.TrackerListItem
import makes.flint.poh.ui.interfaces.FilterView
import makes.flint.poh.ui.main.MainActivity
import makes.flint.poh.ui.tracker.addCoinDialog.AddCoinDialog
import makes.flint.poh.ui.tracker.summary.SummaryViewPager
import makes.flint.poh.ui.tracker.summary.SummaryViewPagerAdapter
import makes.flint.poh.ui.tracker.trackerEntryDialog.TrackerEntryDialog
import makes.flint.poh.ui.tracker.trackerList.TrackerAdapterContractView
import makes.flint.poh.ui.tracker.trackerList.TrackerListAdapter
import rx.functions.Action1

/**
 * TrackerFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
const val CHART_HIDDEN = 0
const val CHART_FULL_SCREEN = 1
const val CHART_OFF_SCREEN = 2

class TrackerFragment : BaseFragment(), TrackerContractView, FilterView {

    private lateinit var trackerConstraint: ConstraintLayout
    private lateinit var trackerRecycler: RecyclerView
    private lateinit var summaryViewPager: SummaryViewPager
    private lateinit var addCoinButton: FloatingActionButton
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var noEntriesPlaceHolder: TextView
    private lateinit var changeChartButton: ImageView
    private lateinit var cancelChartButton: ImageView
    private lateinit var hideSummaryAreaButton: ImageView
    private lateinit var showSummaryAreaButton: ImageView

    private val originalConstraint = ConstraintSet()
    private val noChartConstraint = ConstraintSet()
    private val chartViewConstraint = ConstraintSet()

    private lateinit var trackerPresenter: TrackerContractPresenter
    private lateinit var trackerListAdapter: TrackerAdapterContractView
    private lateinit var summaryPagerAdapter: SummaryViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_tracker, container, false)
        trackerPresenter = getPresenterComponent().provideTrackerPresenter()
        trackerPresenter.attachView(this)
        attachPresenter(trackerPresenter)
        bindViews(view)
        trackerPresenter.initialise()
        return view
    }

    override fun onResume() {
        super.onResume()
        trackerListAdapter.refreshList()
    }

    override fun onDestroy() {
        super.onDestroy()
        trackerListAdapter.onDestroy()
    }

    private fun bindViews(view: View?) {
        view ?: return
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

    override fun initialiseTrackerList() {
        noEntriesPlaceHolder.visibility = View.GONE
        trackerListAdapter = TrackerListAdapter(getPresenterComponent())
        trackerRecycler.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        trackerRecycler.adapter = trackerListAdapter as TrackerListAdapter
        initialiseScrollListener()
    }

    private fun initialiseScrollListener() {
        trackerRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> hideAddCoinFAB()
                    else -> showAddCoinFAB()
                }
            }
        })
    }

    private fun hideAddCoinFAB() {
        if (!trackerRecycler.canScrollVertically(0)) {
            return
        }
        addCoinButton.hide()
    }

    private fun showAddCoinFAB() {
        addCoinButton.show()
    }

    override fun initialiseTrackerListListeners() {
        trackerListAdapter.onTrackerEntrySelected().subscribe {
            makeTrackerEntryDialogFor(it)
        }
        trackerListAdapter.onSummaryPrepared().subscribe {
            println("ONNEXT ${it.currentValueFiatFormatted()}")
            updateSummaryFragment(it)
        }
        trackerListAdapter.onNoEntriesPresent().subscribe(Action1<Boolean> {
            if (!it) {
                hideNoTrackerEntriesMessage()
                return@Action1
            }
            showNoTrackerEntriesMessage()
        })
        trackerListAdapter.onRefreshStateChange().subscribe {
            if (it) {
                showLoading()
                return@subscribe
            }
            hideLoading()
        }
    }

    private fun updateSummaryFragment(summary: Summary) {
        summaryPagerAdapter.setNewSummary(summary)
    }

    private fun hideNoTrackerEntriesMessage() {
        noEntriesPlaceHolder.visibility = View.GONE
    }

    private fun makeTrackerEntryDialogFor(item: TrackerListItem) {
        val fragmentManager = activity.fragmentManager
        val shownCoinDetail = fragmentManager.findFragmentByTag("TrackerEntryDetail")
        shownCoinDetail?.let {
            fragmentManager.beginTransaction().remove(it).commit()
        }
        val newCoinDetail = TrackerEntryDialog.getInstanceFor(item)
        newCoinDetail.show(fragmentManager, "TrackerEntryDetail")
        initialiseTrackerEntryDialogListener(newCoinDetail)
    }

    private fun initialiseTrackerEntryDialogListener(newCoinDetail: TrackerEntryDialog) {
        newCoinDetail.onEntryDeleted().subscribe {
            if (!it) {
                return@subscribe
            }
            trackerListAdapter.refreshList()
        }
    }

    override fun initialiseRefreshListener() {
        val refreshColour = ContextCompat.getColor(context, R.color.colorAccent)
        swipeRefresh.setColorSchemeColors(refreshColour)
        swipeRefresh.setOnRefreshListener {
            (activity as MainActivity).clearSearchTerms()
            trackerListAdapter.refreshList()
        }
    }

    override fun showNoTrackerEntriesMessage() {
        noEntriesPlaceHolder.visibility = View.VISIBLE
    }

    override fun initialiseAddCoinButtonListener() {
        addCoinButton.setOnClickListener {
            showAddCoinDialog()
        }
    }

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
            trackerListAdapter.initialiseTrackerList()
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
}
