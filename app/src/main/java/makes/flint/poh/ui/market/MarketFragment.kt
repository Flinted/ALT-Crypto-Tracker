package makes.flint.poh.ui.market

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.poh.R
import makes.flint.poh.base.BaseFragment
import makes.flint.poh.ui.coinlist.CoinListAdapter
import makes.flint.poh.ui.coinlist.CoinListAdapterContractView

/**
 * MarketFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketFragment: BaseFragment(), MarketContractView {

    // View Bindings
    private lateinit var coinListRecyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

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

    // Overriden Functions
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
    }

}