package makes.flint.alt.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.ViewTreeObserver
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.configuration.START_TRACKER
import makes.flint.alt.ui.interfaces.FilterView
import makes.flint.alt.ui.main.extensions.idToSortMap
import makes.flint.alt.ui.main.extensions.sortToIdMap
import org.jetbrains.anko.support.v4.onPageChangeListener

/**
 * MainActivity
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MainActivity : BaseActivity(), MainContractView {

    // Private Properties
    private lateinit var views: MainActivityViewHolder
    private lateinit var mainPresenter: MainContractPresenter
    private lateinit var viewPagerAdapter: MainViewPagerAdapter
    private var timeTickBroadcastReceiver: BroadcastReceiver? = null

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        views = MainActivityViewHolder(this)
        mainPresenter = getPresenterComponent().provideMainPresenter()
        mainPresenter.attachView(this)
        attachPresenter(mainPresenter)
        mainPresenter.initialise()
        showWelcomeScreenIfRequired(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        views.welcomeScreen.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.emitData()
        timeTickBroadcastReceiver ?: let {
            this.timeTickBroadcastReceiver = makeTimeTickBroadcastReceiver()
        }
        registerReceiver(timeTickBroadcastReceiver, IntentFilter())
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(timeTickBroadcastReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mainPresenter.onDestroy()
    }

    // Initialisation

    // This method waits until all child views are created and then triggers an
    // emission of the cached data to all subscribers.
    override fun initialiseData() {
        views.viewPager.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver
        .OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                views.viewPager.viewTreeObserver.removeOnGlobalLayoutListener(this)
                mainPresenter.emitData()
            }
        })
    }

    private fun showWelcomeScreenIfRequired(savedInstanceState: Bundle?) {
        views.welcomeScreen.show(savedInstanceState)
    }

    private fun makeTimeTickBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (intent.action == Intent.ACTION_TIME_TICK) {
                    mainPresenter.updateSyncTime()
                }
            }
        }
    }

    override fun initialiseViewPager() {
        this.viewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        views.viewPager.adapter = viewPagerAdapter
        views.viewPager.offscreenPageLimit = 1
        views.viewPager.onPageChangeListener {
            onPageSelected {
                val selectedId = when (it) {
                    0 -> R.id.bottom_bar_market
                    else -> R.id.bottom_bar_tracker
                }
                views.bottomBar.selectedItemId = selectedId
            }
        }
    }

    private fun initialiseSortingMenu(menu: Menu) {
        val currentSort = POHSettings.sortPreference
        val id = mainPresenter.getIdForSortType(currentSort) ?: let {
            return
        }
        val currentSortMenuItem = menu.findItem(id)
        currentSortMenuItem.isChecked = true
    }

    override fun initialiseBottomBar(startingTab: String) {
        views.bottomBar.inflateMenu(R.menu.bottom_bar_menu)
        views.bottomBar.setOnNavigationItemSelectedListener({ item ->
            item.isChecked = true
            handleBottomBarSelection(item)
            true
        })
        views.bottomBar.selectedItemId = getStartingTabId(startingTab)
    }

    override fun initialiseSortingMaps() {
        createIdToSortMap()
        createSortToIdMap()
    }

    private fun initialiseSearchMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        views.searchView = searchItem?.actionView as SearchView
        views.searchView.maxWidth = Integer.MAX_VALUE
        views.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false
            override fun onQueryTextChange(newText: String): Boolean {
                getShownFilterView()?.filterFor(newText)
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun getStartingTabId(startingTab: String): Int {
        return when (startingTab) {
            START_TRACKER -> R.id.bottom_bar_tracker
            else -> R.id.bottom_bar_market
        }
    }

    private fun handleBottomBarSelection(item: MenuItem) {
        val itemId = item.itemId
        when (itemId) {
            R.id.bottom_bar_market -> views.viewPager.setCurrentItem(0, true)
            else -> views.viewPager.setCurrentItem(1, true)
        }
    }

    private fun getShownFilterView(): FilterView? {
        val currentPosition = views.viewPager.currentItem
        val fragment = viewPagerAdapter.getFragment(currentPosition)
        if (fragment is FilterView) {
            return fragment
        }
        return null
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu ?: return super.onPrepareOptionsMenu(menu)
        initialiseSearchMenu(menu)
        initialiseSortingMenu(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun updateSortForSelection(id: Int) {
        getSortForId(id)
    }

    private fun getSortForId(id: Int) {
        val sortType = mainPresenter.getSortTypeForId(id) ?: let {
            return
        }
        POHSettings.sortPreference = sortType
        mainPresenter.emitData()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId ?: return false
        item.isChecked = true
        updateSortForSelection(id)
        return super.onOptionsItemSelected(item)
    }

    internal fun clearSearchTerms() {
        views.searchView.isIconified = true
    }

    private fun createSortToIdMap() {
        val map = this.sortToIdMap()
        mainPresenter.storeSortToIdMap(map)
    }

    private fun createIdToSortMap() {
        val map = this.idToSortMap()
        mainPresenter.storeIdToSortMap(map)
    }

    override fun showLoading() {
        viewPagerAdapter.showLoading()
    }
}
