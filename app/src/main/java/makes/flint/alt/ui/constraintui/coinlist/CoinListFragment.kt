package makes.flint.alt.ui.constraintui.coinlist

import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.layoutCoordination.coin
import makes.flint.alt.layoutCoordination.coinToSearch
import makes.flint.alt.layoutCoordination.home
import makes.flint.alt.layoutCoordination.viewTransitions.HomeToCoinDetailTransition
import makes.flint.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapter
import makes.flint.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapterContractView
import makes.flint.alt.ui.constraintui.layoutCoordinator.LayoutCoordinatable
import makes.flint.alt.ui.interfaces.ListScrollController
import makes.flint.alt.ui.search.SearchSummaryCallback

class CoinListFragment : BaseFragment(), CoinListContractView, ListScrollController {

    private lateinit var views: CoinListViewHolder
    private lateinit var coinListPresenter: CoinListContractPresenter
    private lateinit var coinListAdapter: CoinListAdapterContractView

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
                handleScrollChange(yPosition)
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

    private fun handleScrollChange(yPosition: Int) {
        if (yPosition < 2500) {
            hideGoToTopFAB()
            return
        }
        showGoToTopFAB()
    }

    private fun showGoToTopFAB() {
        views.goToTopFAB.show()
    }

    private fun hideGoToTopFAB() {
        views.goToTopFAB.hide()
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
}
