package makes.flint.poh.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import makes.flint.poh.R
import makes.flint.poh.base.BaseActivity
import makes.flint.poh.coinlist.CoinListAdapter
import makes.flint.poh.coinlist.CoinListAdapterContractView

/**
 * MainActivity
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class MainActivity : BaseActivity(), MainContractView {
    // View Bindings
    private lateinit var coinListRecyclerView: RecyclerView

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
        println("xxy ONCREATEOPTIONS")
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
        println("xxy INITIALISING LIST")
        val presenterComponent = getPresenterComponent()
        val coinListAdapter = CoinListAdapter(presenterComponent)
        val layoutManager = LinearLayoutManager(this)
        coinListRecyclerView.layoutManager = layoutManager
        coinListRecyclerView.adapter = coinListAdapter
        this.coinListAdapter = coinListAdapter
    }

    // Private Functions
    private fun bindViews() {
        this.coinListRecyclerView = findViewById(R.id.coin_list_recycler_view)
    }
}