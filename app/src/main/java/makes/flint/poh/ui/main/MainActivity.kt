package makes.flint.poh.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import makes.flint.poh.R
import makes.flint.poh.base.BaseActivity
import makes.flint.poh.ui.coinlist.CoinListAdapter
import makes.flint.poh.ui.coinlist.CoinListAdapterContractView

/**
 * MainActivity
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class MainActivity : BaseActivity(), MainContractView {
    // View Bindings
    private lateinit var coinListRecyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var bottomBar: BottomNavigationView

    // Private Properties
    private lateinit var mainPresenter: MainPresenter

    private lateinit var coinListAdapter: CoinListAdapterContractView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        mainPresenter = getPresenterComponent().provideMainPresenter()
        mainPresenter.attachView(this)
        attachPresenter(mainPresenter)
        mainPresenter.initialise()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId ?: return false
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initialiseListAdapter() {
        val presenterComponent = getPresenterComponent()
        val coinListAdapter = CoinListAdapter(presenterComponent, this)
        val layoutManager = LinearLayoutManager(this)
        coinListRecyclerView.layoutManager = layoutManager
        coinListRecyclerView.adapter = coinListAdapter
        this.coinListAdapter = coinListAdapter
    }

    // Private Functions
    private fun bindViews() {
        this.coinListRecyclerView = findViewById(R.id.coin_list_recycler_view)
        this.swipeRefresh = findViewById(R.id.coin_list_refresh_layout)
        this.bottomBar = findViewById(R.id.navigation_bottom_bar)
    }

    override fun initialiseSwipeRefreshListener() {
        val refreshColour = ContextCompat.getColor(this, R.color.colorAccent)
        swipeRefresh.setColorSchemeColors(refreshColour)
        swipeRefresh.setOnRefreshListener {
            coinListAdapter.refreshList()
        }
    }

    override fun initialiseBottomBar() {
        bottomBar.inflateMenu(R.menu.bottom_bar_menu)
        bottomBar.setOnNavigationItemSelectedListener({ item ->
            item.isChecked = true
            true
        })
        bottomBar.selectedItemId = R.id.bottom_bar_market
    }

    override fun showLoading() {
        super.showLoading()
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        super.hideLoading()
        swipeRefresh.isRefreshing = false
    }
}