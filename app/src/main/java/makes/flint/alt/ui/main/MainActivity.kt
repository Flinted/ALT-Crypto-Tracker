package makes.flint.alt.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.ViewTreeObserver
import com.stephentuso.welcome.WelcomeHelper
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.configuration.START_TRACKER
import makes.flint.alt.ui.interfaces.*
import makes.flint.alt.ui.onboard.OnboardActivity
import org.jetbrains.anko.support.v4.onPageChangeListener

/**
 * MainActivity
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MainActivity : BaseActivity(), MainContractView {

    // View Bindings
    private lateinit var masterLayout: CoordinatorLayout
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var viewPager: ViewPager
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView

    // Private Properties
    private lateinit var mainPresenter: MainContractPresenter
    private lateinit var viewPagerAdapter: MainViewPagerAdapter
    private var timeTickBroadcastReceiver: BroadcastReceiver? = null
    private var welcomeScreen: WelcomeHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        mainPresenter = getPresenterComponent().provideMainPresenter()
        mainPresenter.attachView(this)
        attachPresenter(mainPresenter)
        mainPresenter.initialise()
        showWelcomeScreenIfRequired(savedInstanceState)
    }

    private fun showWelcomeScreenIfRequired(savedInstanceState: Bundle?) {
        welcomeScreen = WelcomeHelper(this, OnboardActivity::class.java)
        welcomeScreen?.show(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        welcomeScreen?.onSaveInstanceState(outState)
    }

    // This method waits until all child views are created and then triggers an
    // emission of the cached data to all subscribers.
    override fun initialiseData() {
        viewPager.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewPager.viewTreeObserver.removeOnGlobalLayoutListener(this)
                mainPresenter.emitData()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.emitData()
        timeTickBroadcastReceiver ?: let {
            this.timeTickBroadcastReceiver = makeTimeTickBroadcastReceiver()
        }
        registerReceiver(timeTickBroadcastReceiver, IntentFilter())
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

    override fun onPause() {
        super.onPause()
        unregisterReceiver(timeTickBroadcastReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mainPresenter.onDestroy()
    }

    private fun bindViews() {
        this.bottomBar = findViewById(R.id.navigation_bottom_bar)
        this.viewPager = findViewById(R.id.fragment_container)
        this.toolbar = findViewById(R.id.toolbar)
        this.masterLayout = findViewById(R.id.master_layout)
        setSupportActionBar(toolbar)
    }

    override fun initialiseViewPager() {
        this.viewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 1
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

    override fun initialiseBottomBar(startingTab: String) {
        bottomBar.inflateMenu(R.menu.bottom_bar_menu)
        bottomBar.setOnNavigationItemSelectedListener({ item ->
            item.isChecked = true
            handleBottomBarSelection(item)
            true
        })
        bottomBar.selectedItemId = getStartingTabId(startingTab)
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
        menu ?: return super.onPrepareOptionsMenu(menu)
        initialiseSearchMenu(menu)
        initialiseSortingMenu(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun initialiseSortingMenu(menu: Menu) {
        val currentSort = POHSettings.sortPreference
        val id = mainPresenter.getIdForSortType(currentSort) ?: let {
            return
        }
        val currentSortMenuItem = menu.findItem(id)
        currentSortMenuItem.isChecked = true
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

    override fun initialiseSortingMaps() {
        createIdToSortMap()
        createSortToIdMap()
    }

    private fun initialiseSearchMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false
            override fun onQueryTextChange(newText: String): Boolean {
                getShownFilterView()?.filterFor(newText)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId ?: return false
        item.isChecked = true
        updateSortForSelection(id)
        return super.onOptionsItemSelected(item)
    }


    internal fun clearSearchTerms() {
        searchView.isIconified = true
    }

    private fun createSortToIdMap() {
        val map = hashMapOf(
                SORT_RANK to R.id.sort_rank_ascending,
                SORT_RANK_REV to R.id.sort_rank_descending,
                SORT_NAME to R.id.sort_alphabetical_ascending,
                SORT_NAME_REV to R.id.sort_alphabetical_descending,
                SORT_VOLUME to R.id.sort_volume_ascending,
                SORT_VOLUME_REV to R.id.sort_volume_descending,
                SORT_TWENTY_FOUR_HOUR to R.id.sort_24H_ascending,
                SORT_TWENTY_FOUR_HOUR_REV to R.id.sort_24H_descending,
                SORT_ONE_HOUR to R.id.sort_1H_ascending,
                SORT_ONE_HOUR_REV to R.id.sort_1H_descending,
                SORT_SEVEN_DAY to R.id.sort_7D_ascending,
                SORT_SEVEN_DAY_REV to R.id.sort_1H_descending
        )
        mainPresenter.storeSortToIdMap(map)
    }

    private fun createIdToSortMap() {
        val map = hashMapOf(
                R.id.sort_rank_ascending to SORT_RANK,
                R.id.sort_rank_descending to SORT_RANK_REV,
                R.id.sort_alphabetical_ascending to SORT_NAME,
                R.id.sort_alphabetical_descending to SORT_RANK_REV,
                R.id.sort_1H_ascending to SORT_ONE_HOUR,
                R.id.sort_1H_descending to SORT_ONE_HOUR_REV,
                R.id.sort_24H_ascending to SORT_TWENTY_FOUR_HOUR,
                R.id.sort_24H_descending to SORT_TWENTY_FOUR_HOUR_REV,
                R.id.sort_7D_ascending to SORT_SEVEN_DAY,
                R.id.sort_7D_descending to SORT_SEVEN_DAY_REV,
                R.id.sort_volume_ascending to SORT_VOLUME,
                R.id.sort_volume_descending to SORT_VOLUME_REV
        )
        mainPresenter.storeIdToSortMap(map)
    }
}
