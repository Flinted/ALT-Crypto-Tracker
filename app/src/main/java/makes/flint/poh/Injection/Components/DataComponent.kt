package makes.flint.poh.injection.components

import dagger.Component
import makes.flint.poh.injection.modules.DataModule
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.dataManagers.ApiManager
import makes.flint.poh.data.dataController.dataManagers.RealmManager
import javax.inject.Singleton

/**
 * DataComponent
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
@Singleton
@Component(modules = arrayOf(DataModule::class))
interface DataComponent {
    fun provideDataController(): DataController

    fun provideApiManager(): ApiManager

    fun provideRealmManager(): RealmManager
}