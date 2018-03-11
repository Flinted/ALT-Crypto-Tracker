package makes.flint.alt.ui.market

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary.CoinDetailSummary
import makes.flint.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapter
import makes.flint.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapterContractView
import makes.flint.alt.ui.interfaces.FilterView
import makes.flint.alt.ui.main.MainActivity

/**
 * MarketFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class MarketFragment : BaseFragment(), MarketContractView, FilterView {

    // Properties

    private lateinit var views: MarketFragmentViewHolder
    private lateinit var marketPresenter: MarketContractPresenter
    private lateinit var coinListAdapter: CoinListAdapterContractView

    // Lifecycle

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_market, container, false)
        marketPresenter = getPresenterComponent().provideMarketPresenter()
        marketPresenter.attachView(this)
        this.attachPresenter(marketPresenter)
        view?.let {
            this.views = MarketFragmentViewHolder(view)
        }
        marketPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        marketPresenter.onDestroy()
        coinListAdapter.onDestroy()
    }

    // Overrides

    override fun initialiseListAdapter() {
        val presenterComponent = getPresenterComponent()
        val coinListAdapter = CoinListAdapter(presenterComponent)
        val layoutManager = LinearLayoutManager(context)
        views.coinListRecyclerView.layoutManager = layoutManager
        views.coinListRecyclerView.adapter = coinListAdapter
        this.coinListAdapter = coinListAdapter
    }

    override fun initialiseSwipeRefreshListener() {
        val refreshColour = ContextCompat.getColor(context, R.color.colorAccent)
        views.swipeRefresh.setColorSchemeColors(refreshColour)
        views.swipeRefresh.setOnRefreshListener {
            (activity as MainActivity).clearSearchTerms()
            marketPresenter.refresh()
        }
    }

    override fun initialiseScrollListener() {
        views.coinListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var yPosition = 0
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                yPosition += dy
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun initialiseFABonClick() {
        views.goToTopFAB.setOnClickListener {
            views.coinListRecyclerView.smoothScrollToPosition(0)
        }
    }

    override fun initialiseAdapterListeners() {
        coinListAdapter.onCoinSelected().subscribe { coinSymbol -> marketPresenter.onCoinSelected(coinSymbol) }
    }

    override fun hideProgressSpinner() {
        views.progressSpinner.visibility = View.GONE
        views.swipeRefresh.visibility = View.VISIBLE
    }

    override fun updateMarketSummary(marketSummary: MarketSummaryResponse) {
    }

    override fun updateLastSyncTime(lastSync: String) {
    }

    override fun showDialogForCoin(coinSymbol: String) {
        val fragmentManager = activity.supportFragmentManager
        val newCoinDetail = CoinDetailSummary.getInstanceFor(coinSymbol)
        fragmentManager.beginTransaction().replace(R.id.frame_bottom, newCoinDetail).commit()

    }

    override fun showLoading() {
        views.swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        views.swipeRefresh.isRefreshing = false
    }

    override fun filterFor(input: String) {
        coinListAdapter.filterFor(input)
    }
}
