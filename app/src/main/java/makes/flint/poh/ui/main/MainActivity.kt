package makes.flint.poh.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import makes.flint.poh.R
import makes.flint.poh.base.BaseActivity
import makes.flint.poh.ui.interfaces.FilterView
import makes.flint.poh.ui.market.MarketFragment
import makes.flint.poh.ui.tracker.TrackerFragment

/**
 * MainActivity
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MainActivity : BaseActivity(), MainContractView {

    // View Bindings
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var toolbar: Toolbar

    // Private Properties
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        mainPresenter = getPresenterComponent().provideMainPresenter()
        mainPresenter.attachView(this)
        attachPresenter(mainPresenter)
        mainPresenter.initialise()
    }

    private fun bindViews() {
        this.bottomBar = findViewById(R.id.navigation_bottom_bar)
        this.toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun initialiseBottomBar() {
        bottomBar.inflateMenu(R.menu.bottom_bar_menu)
        bottomBar.setOnNavigationItemSelectedListener({ item ->
            item.isChecked = true
            handleBottomBarSelection(item)
            true
        })
        bottomBar.selectedItemId = R.id.bottom_bar_market
    }

    private fun handleBottomBarSelection(item: MenuItem) {
        val itemId = item.itemId
        var fragment = supportFragmentManager.findFragmentByTag(itemId.toString())
        fragment?.let {
            showFragment(it, itemId)
            return
        }
        fragment = when (itemId) {
            R.id.bottom_bar_market -> MarketFragment()
            R.id.bottom_bar_tracker -> TrackerFragment()
            else -> MarketFragment()
        }
        addFragment(fragment, itemId)
    }

    private fun addFragment(fragment: Fragment, id: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, fragment, id.toString())
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
        hideAlternateFragment(transaction, id)
        transaction.commit()
    }

    private fun showFragment(fragment: Fragment, id: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_left, R.anim
                .slide_out_left)
        transaction.show(fragment)
        hideAlternateFragment(transaction, id)
        transaction.commit()
    }

    private fun hideAlternateFragment(transaction: FragmentTransaction, id: Int) {
        val fragmentToHideId = when (id) {
            R.id.bottom_bar_market -> R.id.bottom_bar_tracker
            R.id.bottom_bar_tracker -> R.id.bottom_bar_market
            else -> R.id.bottom_bar_tracker
        }
        val fragment = supportFragmentManager.findFragmentByTag(fragmentToHideId.toString())
        fragment?.let {
            transaction.hide(it)
        }
    }

    private fun getShownFilterView(): FilterView? {
        val fragmentId = bottomBar.selectedItemId.toString()
        val fragment = supportFragmentManager.findFragmentByTag(fragmentId) ?: return null
        if (fragment is FilterView) {
            return fragment
        }
        return null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getShownFilterView()?.filterFor(newText)
                return true
            }
        })
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId ?: return false
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}