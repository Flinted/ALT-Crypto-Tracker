package makes.flint.alt.ui.tracker

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.ui.constraintui.layoutCoordinator.LayoutCoordinatable
import makes.flint.alt.ui.constraintui.layoutCoordinator.addCoin
import makes.flint.alt.ui.interfaces.FilterView
import makes.flint.alt.ui.tracker.trackerEntryDialog.TrackerEntryDialog
import makes.flint.alt.ui.tracker.trackerList.TrackerAdapterContractView
import makes.flint.alt.ui.tracker.trackerList.TrackerListAdapter
import rx.functions.Action1

/**
 * TrackerListFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
const val CHART_HIDDEN = 0
const val CHART_FULL_SCREEN = 1
const val CHART_OFF_SCREEN = 2

class TrackerListFragment : BaseFragment(), TrackerContractView, FilterView {

    // Properties

    private lateinit var views: TrackerFragmentViewholder
    private lateinit var trackerPresenter: TrackerContractPresenter
    private lateinit var trackerListAdapter: TrackerAdapterContractView

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
                return@Action1
            }
            showNoTrackerEntriesMessage()
        })
    }

    override fun initialiseRefreshListener() {
        val refreshColour = ContextCompat.getColor(context, R.color.colorAccent)
        views.swipeRefresh.setColorSchemeColors(refreshColour)
        views.swipeRefresh.setOnRefreshListener {
            trackerPresenter.refreshCache()
        }
    }

    override fun initialiseAddCoinButtonListener() {
        views.addCoinButton.setOnClickListener {
            showAddCoinDialog()
        }
    }

    override fun hideProgressSpinner() {
        views.swipeRefresh.visibility = View.VISIBLE
    }

    override fun showNoTrackerEntriesMessage() {
        views.swipeRefresh.visibility = View.GONE
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

    private fun showAddCoinDialog() {
        (activity as LayoutCoordinatable).updateLayout(addCoin)
    }
}
