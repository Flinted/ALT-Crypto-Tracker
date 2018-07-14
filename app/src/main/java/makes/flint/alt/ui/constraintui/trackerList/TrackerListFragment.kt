package makes.flint.alt.ui.constraintui.trackerList

import android.graphics.Point
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.data.trackerListItem.TrackerListItem
import makes.flint.alt.layoutCoordination.tracker
import makes.flint.alt.layoutCoordination.trackerSearch
import makes.flint.alt.ui.constraintui.layoutCoordinator.LayoutCoordinatable
import makes.flint.alt.ui.constraintui.trackerEntryDetail.TrackerDetailDialog
import makes.flint.alt.ui.constraintui.trackerList.trackerListAdapter.TrackerAdapterContractView
import makes.flint.alt.ui.constraintui.trackerList.trackerListAdapter.TrackerListAdapter
import makes.flint.alt.ui.interfaces.FilterView
import makes.flint.alt.ui.interfaces.ListScrollController
import makes.flint.alt.ui.search.SearchSummaryCallback

class TrackerListFragment : BaseFragment(), TrackerContractView, FilterView, ListScrollController {

    // Properties

    private lateinit var views: TrackerFragmentViewholder
    private lateinit var trackerPresenter: TrackerContractPresenter
    private lateinit var trackerListAdapter: TrackerAdapterContractView

    // Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tracker_list, container, false)
        trackerPresenter = getPresenterComponent().provideTrackerPresenter()
        trackerPresenter.attachView(this)
        attachPresenter(trackerPresenter)
        this.views = TrackerFragmentViewholder(view)
        views.summarySearchBar.initialise(requireContext())
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
        views.noEntriesMessage.visibility = View.GONE
        trackerListAdapter = TrackerListAdapter(getPresenterComponent(), getScreenWidth())
        views.trackerRecycler.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        views.trackerRecycler.adapter = trackerListAdapter as TrackerListAdapter
    }

    private fun getScreenWidth(): Int {
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val width = size.x
        val padding = views.swipeRefresh.paddingStart * 3
        return width - padding
    }

    override fun initialiseTrackerListListeners() {
        trackerListAdapter.onTrackerEntrySelected().subscribe {
            makeTrackerEntryDialogFor(it)
        }
        val subscription = trackerListAdapter.onNoEntriesPresent()
        subscription.first.subscribe({ noEntriesPresent ->
                                         handleTrackerEntriesChange(noEntriesPresent)
                                     })
        handleTrackerEntriesChange(subscription.second)
    }

    private fun handleTrackerEntriesChange(noEntriesPresent: Boolean) {
        if (!noEntriesPresent) {
            hideNoTrackerEntriesMessage()
            return
        }
        showNoTrackerEntriesMessage()
    }

    override fun initialiseRefreshListener() {
        val refreshColour = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        views.swipeRefresh.setColorSchemeColors(refreshColour)
        views.swipeRefresh.setOnRefreshListener {
            trackerPresenter.refreshCache()
        }
    }

    override fun initialiseSearchBar() {
        views.summarySearchBar.setCallback(object : SearchSummaryCallback {
            override fun keyboardRequested() {
                showKeyboard()
            }

            override fun keyboardDismissed(windowToken: IBinder) {
                hideKeyboard(windowToken)
            }

            override fun searchStateRequested() {
                (activity as LayoutCoordinatable).updateLayout(trackerSearch)
            }

            override fun cancelSearchRequested() {
                (activity as LayoutCoordinatable).updateLayout(tracker)
            }

            override fun newSearchQuery(query: String) {
                trackerListAdapter.filterFor(query)
            }
        })
    }

    override fun displayMarketSummary(marketSummary: MarketSummaryResponse?) {
        views.summarySearchBar.displayMarketSummary(requireContext(), marketSummary)
    }

    override fun hideProgressSpinner() {
        views.swipeRefresh.visibility = View.VISIBLE
    }

    override fun showNoTrackerEntriesMessage() {
        views.swipeRefresh.visibility = View.GONE
        views.noEntriesMessage.visibility = View.VISIBLE
    }

    private fun hideNoTrackerEntriesMessage() {
        views.swipeRefresh.visibility = View.VISIBLE
        views.noEntriesMessage.visibility = View.GONE
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

    override fun stopListScroll() {
        views.trackerRecycler.stopScroll()
    }
    // Private Functions

    private fun makeTrackerEntryDialogFor(item: TrackerListItem) {
        val fragmentManager = activity?.fragmentManager
        val shownCoinDetail = fragmentManager?.findFragmentByTag("TrackerEntryDetail")
        shownCoinDetail?.let {
            fragmentManager.beginTransaction().remove(it).commit()
        }
        val newCoinDetail = TrackerDetailDialog.getInstanceFor(item)
        newCoinDetail.show(fragmentManager, "TrackerEntryDetail")
    }
}
