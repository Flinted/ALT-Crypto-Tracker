package did.chris.alt.ui.constraintui.trackerList

import android.graphics.Point
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import did.chris.alt.R
import did.chris.alt.base.BaseFragment
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse
import did.chris.alt.data.trackerListItem.TrackerListItem
import did.chris.alt.layoutCoordination.home
import did.chris.alt.layoutCoordination.searchToTracker
import did.chris.alt.layoutCoordination.trackerToSearch
import did.chris.alt.ui.constraintui.addCoin.AddCoinDialogFragment
import did.chris.alt.ui.constraintui.layoutCoordinator.LayoutCoordinatable
import did.chris.alt.ui.constraintui.trackerEntryDetail.TrackerDetailDialog
import did.chris.alt.ui.constraintui.trackerList.trackerListAdapter.TrackerAdapterContractView
import did.chris.alt.ui.constraintui.trackerList.trackerListAdapter.TrackerListAdapter
import did.chris.alt.ui.interfaces.FilterView
import did.chris.alt.ui.interfaces.ListScrollController
import did.chris.alt.ui.search.SearchSummaryCallback

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
        initialiseBackButton()
        trackerPresenter.initialise()
        return view
    }

    private fun initialiseBackButton() {
        views.backButton.setOnClickListener {
            (activity as LayoutCoordinatable).updateLayout(home)
        }
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

    override fun initialiseAddEntryButton() {
        views.addButton.setOnClickListener {
            val fragmentManager = activity?.fragmentManager
            val shownCoinDetail = fragmentManager?.findFragmentByTag("AddCoinDialog")
            shownCoinDetail?.let {
                fragmentManager.beginTransaction().remove(it).commit()
            }
            val newCoinDetail = AddCoinDialogFragment.createForAsset(null)
            newCoinDetail.show(fragmentManager, "AddCoinDialog")
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
                (activity as LayoutCoordinatable).updateLayout(trackerToSearch)
            }

            override fun cancelSearchRequested() {
                (activity as LayoutCoordinatable).updateLayout(searchToTracker)
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
