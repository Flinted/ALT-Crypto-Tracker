package did.chris.alt.ui.constraintui.coinlist

import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import did.chris.alt.R
import did.chris.alt.base.BaseFragment
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse
import did.chris.alt.layoutCoordination.coin
import did.chris.alt.layoutCoordination.coinToSearch
import did.chris.alt.layoutCoordination.home
import did.chris.alt.layoutCoordination.viewTransitions.HomeToCoinDetailTransition
import did.chris.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapter
import did.chris.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapterContractView
import did.chris.alt.ui.constraintui.layoutCoordinator.LayoutCoordinatable
import did.chris.alt.ui.interfaces.ListScrollController
import did.chris.alt.ui.search.SearchSummaryCallback

class CoinListFragment : BaseFragment(), CoinListContractView, ListScrollController {

    // Properties
    private lateinit var views: CoinListViewHolder
    private lateinit var coinListPresenter: CoinListContractPresenter
    private lateinit var coinListAdapter: CoinListAdapterContractView

    // Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coin_list, container, false)
        coinListPresenter = getPresenterComponent().provideCoinListPresenter()
        coinListPresenter.attachView(this)
        this.attachPresenter(coinListPresenter)
        this.views = CoinListViewHolder(view)
        views.coinSearchBar.initialise(requireContext())
        coinListPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        coinListAdapter.onDestroy()
        coinListPresenter.onDestroy()
    }

    // Overrides
    override fun initialiseSearchOnClick() {
        views.coinSearchBar.setCallback(object : SearchSummaryCallback {
            override fun keyboardRequested() {
                showKeyboard()
            }

            override fun keyboardDismissed(windowToken: IBinder) {
                hideKeyboard(windowToken)
            }

            override fun searchStateRequested() {
                (activity as LayoutCoordinatable).updateLayout(coinToSearch)
            }

            override fun cancelSearchRequested() {
                (activity as LayoutCoordinatable).updateLayout(home)
            }

            override fun newSearchQuery(query: String) {
                coinListAdapter.filterFor(query)
                hideGoToTopFAB()
            }

        })
    }

    override fun initialiseListAdapter() {
        val presenterComponent = getPresenterComponent()
        val coinListAdapter = CoinListAdapter(presenterComponent)
        val layoutManager = LinearLayoutManager(context)
        views.coinList.layoutManager = layoutManager
        views.coinList.adapter = coinListAdapter
        this.coinListAdapter = coinListAdapter
        coinListAdapter.onCoinSelected()
            .subscribe { coinSymbol -> coinListPresenter.onCoinSelected(coinSymbol) }
    }

    override fun initialiseSwipeRefreshListener() {
        val refreshColour = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        views.swipeRefresh.setColorSchemeColors(refreshColour)
        views.swipeRefresh.setOnRefreshListener {
            coinListPresenter.refresh()
        }
    }

    override fun initialiseScrollListener() {
        views.coinList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var yPosition = 0
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                yPosition += dy
                coinListPresenter.assessScrollChange(yPosition)
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun showDialogForCoin(coinSymbol: String) {
        hideKeyboard(views.coinList.windowToken)
        val coinDetailTransition = HomeToCoinDetailTransition(requireContext(), coinSymbol)
        (activity as LayoutCoordinatable).updateLayout(coin, coinDetailTransition)
    }

    override fun initialiseFABonClick() {
        views.goToTopFAB.setOnClickListener {
            views.coinList.smoothScrollToPosition(0)
            hideGoToTopFAB()
        }
    }

    override fun displayMarketSummary(marketSummaryResponse: MarketSummaryResponse?) {
        views.coinSearchBar.displayMarketSummary(requireContext(), marketSummaryResponse)
    }

    override fun showLoading() {
        super.showLoading()
        views.swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        super.hideLoading()
        views.swipeRefresh.isRefreshing = false
    }

    override fun stopListScroll() {
        views.coinList.stopScroll()
    }

    override fun showGoToTopFAB() {
        views.goToTopFAB.show()
    }

    override fun hideGoToTopFAB() {
        views.goToTopFAB.hide()
    }
}
