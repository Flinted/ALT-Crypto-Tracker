package makes.flint.poh.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import makes.flint.poh.R
import makes.flint.poh.base.BaseActivity
import makes.flint.poh.ui.interfaces.FilterView
import org.jetbrains.anko.support.v4.onPageChangeListener

/**
 * MainActivity
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MainActivity : BaseActivity(), MainContractView {

    // View Bindings
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var viewPager: ViewPager
    private lateinit var toolbar: Toolbar

    // Private Properties
    private lateinit var mainPresenter: MainPresenter
    private lateinit var viewPagerAdapter: ViewPagerAdapter

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
        this.viewPager = findViewById(R.id.fragment_container)
        this.toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun initialiseViewPager() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        viewPager.onPageChangeListener {
            onPageSelected {
                val selectedId = when (it) {
                    0 -> R.id.bottom_bar_market
                    else -> R.id.bottom_bar_tracker
                }
                bottomBar.selectedItemId = selectedId
            }
        }
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
        when (itemId) {
            R.id.bottom_bar_market -> viewPager.setCurrentItem(0, true)
            else -> viewPager.setCurrentItem(1, true)
        }
    }

    private fun getShownFilterView(): FilterView? {
        val currentPosition = viewPager.currentItem
        val fragment = viewPagerAdapter.getFragment(currentPosition)
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