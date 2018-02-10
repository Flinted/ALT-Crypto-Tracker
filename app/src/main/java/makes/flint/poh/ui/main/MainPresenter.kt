package makes.flint.poh.ui.main

import makes.flint.poh.base.BasePresenter
import makes.flint.poh.configuration.POHSettings
import makes.flint.poh.data.dataController.DataController

/**
 * MainPresenter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MainPresenter(private var dataController: DataController) : BasePresenter<MainContractView>(), MainContractPresenter {

    private lateinit var idToSortMap: HashMap<Int, Int>
    private lateinit var sortToIdMap: HashMap<Int, Int>

    override fun initialise() {
        val startingTab = POHSettings.startingScreen
        view?.initialiseSortingMaps()
        view?.initialiseViewPager()
        view?.initialiseBottomBar(startingTab)
        view?.initialiseData()
    }

    override fun emitData() {
        dataController.refreshRequested()
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
}
