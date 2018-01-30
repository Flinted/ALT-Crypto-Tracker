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
import android.widget.TextView
import makes.flint.poh.R
import makes.flint.poh.base.BaseFragment
import makes.flint.poh.ui.coinlist.CoinListAdapter
import makes.flint.poh.ui.coinlist.CoinListAdapterContractView
import makes.flint.poh.ui.interfaces.FilterView

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

    // Properties
    private lateinit var marketPresenter: MarketPresenter
    private lateinit var coinListAdapter: CoinListAdapterContractView

    // Lifecycle
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.content_main, container, false)
        marketPresenter = getPresenterComponent().provideMarketPresenter()
        marketPresenter.attachView(this)
        this.attachPresenter(marketPresenter)
        bindViews(view)
        marketPresenter.initialise()
        return view
    }

    // Overridden Functions
    override fun initialiseListAdapter() {
        val presenterComponent = getPresenterComponent()
        val coinListAdapter = CoinListAdapter(presenterComponent, this)
        val layoutManager = LinearLayoutManager(context)
        coinListRecyclerView.layoutManager = layoutManager
        coinListRecyclerView.adapter = coinListAdapter
        this.coinListAdapter = coinListAdapter
    }

    override fun initialiseSwipeRefreshListener() {
        val refreshColour = ContextCompat.getColor(context, R.color.colorAccent)
        swipeRefresh.setColorSchemeColors(refreshColour)
        swipeRefresh.setOnRefreshListener {
            coinListAdapter.refreshList()
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

    override fun showLoading() {
        super.showLoading()
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        super.hideLoading()
        swipeRefresh.isRefreshing = false
    }

    // Private Functions
    private fun bindViews(view: View?) {
        view ?: return
        this.coinListRecyclerView = view.findViewById(R.id.coin_list_recycler_view)
        this.swipeRefresh = view.findViewById(R.id.coin_list_refresh_layout)
        this.goToTopFAB = view.findViewById(R.id.coin_list_FAB)
        this.lastSyncTime = view.findViewById(R.id.last_sync_time)
    }

    override fun filterFor(input: String) {
        coinListAdapter.filterFor(input)
    }
}
