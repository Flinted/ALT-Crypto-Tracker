package makes.flint.alt.ui.constraintui.coinlist

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.layoutCoordination.coin
import makes.flint.alt.layoutCoordination.home
import makes.flint.alt.layoutCoordination.search
import makes.flint.alt.layoutCoordination.viewTransitions.HomeToCoinDetailTransition
import makes.flint.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapter
import makes.flint.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapterContractView
import makes.flint.alt.ui.constraintui.layoutCoordinator.LayoutCoordinatable
import makes.flint.alt.ui.interfaces.ListScrollController


/**
 * CoinListFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
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
        coinListPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        coinListAdapter.onDestroy()
        coinListPresenter.onDestroy()
    }

    override fun initialiseSearchOnClick() {
        views.coinSearchButton.setOnClickListener {
            (activity as LayoutCoordinatable).updateLayout(search)
            views.marketSummary.visibility = View.INVISIBLE
            views.coinSearch.isEnabled = true
            views.coinSearch.visibility = View.VISIBLE
            views.coinSearchCancelButton.visibility = View.VISIBLE
            views.coinSearchButton.visibility = View.GONE
            views.coinSearch.requestFocus()
        }
        views.marketSummary.setOnClickListener {
            (activity as LayoutCoordinatable).updateLayout(search)
        }

        views.coinSearch.setOnClickListener {
            (activity as LayoutCoordinatable).updateLayout(search)
            views.coinSearch.requestFocus()
        }
        views.coinSearchCancelButton.setOnClickListener {
            views.coinList.stopScroll()
            (activity as LayoutCoordinatable).updateLayout(home)
            views.coinSearch.text.clear()
            views.coinSearch.isEnabled = false
            views.marketSummary.visibility = View.VISIBLE
            views.coinSearch.visibility = View.INVISIBLE
            views.coinSearchCancelButton.visibility = View.GONE
            views.coinSearchButton.visibility = View.VISIBLE
            hideKeyboard(views.coinSearch.windowToken)
        }
        views.coinSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                coinListAdapter.filterFor(p0.toString())
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
        hideKeyboard(views.coinSearch.windowToken)
        val coinDetailTransition = HomeToCoinDetailTransition(requireContext(), coinSymbol)
        (activity as LayoutCoordinatable).updateLayout(coin, coinDetailTransition)
    }

    override fun initialiseFABonClick() {
        views.goToTopFAB.setOnClickListener {
            views.coinList.smoothScrollToPosition(0)
        }
    }

    override fun displayMarketSummary(marketSummaryResponse: MarketSummaryResponse?) {
        marketSummaryResponse ?: return
        views.marketTotalCap.text = getString(
            R.string.market_summary_marketcap, marketSummaryResponse.marketCapUSDFormatted()
        )
        views.marketCount.text = getString(
            R.string.market_summary_count,
            marketSummaryResponse.marketData.numberItems
        )
        views.marketVolume.text = getString(
            R.string.market_summary_volume, marketSummaryResponse.volume24HUSDFormatted()
        )
        views.marketChange1d.text = getString(
            R.string.market_summary_1d,
            marketSummaryResponse.marketData.twentyFourHourAverageFormatted()
        )
        views.marketChange1w.text = getString(
            R.string.market_summary_1w, marketSummaryResponse.marketData.sevenDayAverageFormatted()
        )
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
