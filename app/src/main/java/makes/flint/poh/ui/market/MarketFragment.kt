package makes.flint.poh.ui.market

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import makes.flint.poh.R
import makes.flint.poh.base.BaseFragment
import makes.flint.poh.errors.ErrorHandler
import makes.flint.poh.ui.interfaces.FilterView
import makes.flint.poh.ui.main.MainActivity
import makes.flint.poh.ui.market.coinDetail.CoinDetailDialog
import makes.flint.poh.ui.market.coinlist.CoinListAdapter
import makes.flint.poh.ui.market.coinlist.CoinListAdapterContractView

/**
 * MarketFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketFragment : BaseFragment(), MarketContractView, FilterView {

    // View Bindings

    private lateinit var coinListRecyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var goToTopFAB: FloatingActionButton
    private lateinit var lastSyncTime: TextView
    private lateinit var marketSummary: TextView

    // Properties

    private lateinit var marketPresenter: MarketContractPresenter
    private lateinit var coinListAdapter: CoinListAdapterContractView

    // Lifecycle

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_market, container, false)
        marketPresenter = getPresenterComponent().provideMarketPresenter()
        marketPresenter.attachView(this)
        this.attachPresenter(marketPresenter)
        bindViews(view)
        marketPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        marketPresenter.onDestroy()
        coinListAdapter.onDestroy()
    }

    // Overridden Functions

    override fun initialiseListAdapter() {
        val presenterComponent = getPresenterComponent()
        val coinListAdapter = CoinListAdapter(presenterComponent)
        val layoutManager = LinearLayoutManager(context)
        coinListRecyclerView.layoutManager = layoutManager
        coinListRecyclerView.adapter = coinListAdapter
        this.coinListAdapter = coinListAdapter
    }

    override fun initialiseSwipeRefreshListener() {
        val refreshColour = ContextCompat.getColor(context, R.color.colorAccent)
        swipeRefresh.setColorSchemeColors(refreshColour)
        swipeRefresh.setOnRefreshListener {
            (activity as MainActivity).clearSearchTerms()
            marketPresenter.refresh()
        }
    }

    override fun initialiseScrollListener() {
        coinListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var yPosition = 0
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                yPosition += dy
                handleScrollChange(yPosition)
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun initialiseFABonClick() {
        goToTopFAB.setOnClickListener {
            coinListRecyclerView.smoothScrollToPosition(0)
        }
    }

    override fun initialiseAdapterListeners() {
        coinListAdapter.onCoinSelected().subscribe { coinSymbol -> marketPresenter.onCoinSelected(coinSymbol) }
    }

    override fun updateMarketSummary(oneHour: String, twentyFourHour: String, sevenDay: String, coins: Int) {
        val summaryString = getString(R.string.market_summary, twentyFourHour, sevenDay, coins)
        this.marketSummary.text = summaryString
    }

    override fun updateLastSyncTime(lastSync: String?) {
        lastSync ?: let {
            showError(ErrorHandler.ERROR_SYNC_TIMEOUT)
            return
        }
        lastSyncTime.text = lastSync
    }

    override fun showDialogForCoin(coinSymbol: String) {
        val fragmentManager = activity.fragmentManager
        val shownCoinDetail = fragmentManager.findFragmentByTag("CoinDetailDialog")
        shownCoinDetail?.let {
            fragmentManager.beginTransaction().remove(it).commit()
        }
        val newCoinDetail = CoinDetailDialog.getInstanceFor(coinSymbol)
        newCoinDetail.show(fragmentManager, "CoinDetailDialog")
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun filterFor(input: String) {
        coinListAdapter.filterFor(input)
    }

    // Private Functions
    private fun bindViews(view: View?) {
        view ?: return
        this.coinListRecyclerView = view.findViewById(R.id.market_recycler_view)
        this.swipeRefresh = view.findViewById(R.id.coin_list_refresh_layout)
        this.goToTopFAB = view.findViewById(R.id.coin_list_FAB)
        this.lastSyncTime = view.findViewById(R.id.market_bottom_ticker)
        this.marketSummary = view.findViewById(R.id.market_top_ticker)
    }

    private fun handleScrollChange(yPosition: Int) {
        if (yPosition < 2500) {
            hideGoToTopFAB()
            return
        }
        showGoToTopFAB()
    }

    private fun showGoToTopFAB() {
        lastSyncTime.gravity = Gravity.END
        goToTopFAB.show()
    }

    private fun hideGoToTopFAB() {
        lastSyncTime.gravity = Gravity.CENTER
        goToTopFAB.hide()
    }
}
