package makes.flint.alt.ui.main

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.errors.ErrorHandler
import rx.Subscription
import java.util.*

/**
 * MainPresenter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class MainPresenter(private var dataController: DataController) : BasePresenter<MainContractView>(), MainContractPresenter {

    // Properties

    private var errorSubscription: Subscription? = null
    private lateinit var idToSortMap: HashMap<Int, Int>
    private lateinit var sortToIdMap: HashMap<Int, Int>

    // Lifecycle

    override fun initialise() {
        val startingTab = POHSettings.startingScreen
        this.initialiseErrorSubscription()
        view?.initialiseSortingMaps()
        view?.initialiseViewPager()
        view?.initialiseBottomBar(startingTab)
        view?.initialiseData()
    }

    override fun onDestroy() {
        this.errorSubscription?.unsubscribe()
        this.errorSubscription = null
    }

    // Overrides

    override fun emitData() {
        val isRefreshing = dataController.refreshRequested()
        if (isRefreshing) {
            view?.showLoading()
        }
    }

    override fun updateSyncTime() {
        dataController.emitLastSyncTime()
    }

    override fun storeIdToSortMap(map: HashMap<Int, Int>) {
        this.idToSortMap = map
    }

    override fun storeSortToIdMap(map: HashMap<Int, Int>) {
        this.sortToIdMap = map
    }

    override fun getSortTypeForId(id: Int): Int? {
        return idToSortMap[id]
    }

    override fun getIdForSortType(currentSort: Int): Int? {
        return sortToIdMap[currentSort]
    }

    // Private Functions

    private fun initialiseErrorSubscription() {
        this.errorSubscription = dataController.getErrorSubscription().subscribe {
            this.view?.showError(ErrorHandler.GENERAL_ERROR)
        }
    }
}
