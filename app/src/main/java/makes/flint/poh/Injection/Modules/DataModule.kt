package makes.flint.poh.Injection.Modules

import dagger.Module
import dagger.Provides
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.dataManagers.ApiManager
import makes.flint.poh.data.dataController.dataManagers.RealmManager
import javax.inject.Singleton

/**
 * DataModule
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
@Module open class DataModule() {

    @Provides @Singleton fun provideDataController(apiManager: ApiManager, realmManager: RealmManager): DataController {
        return DataController(apiManager, realmManager)
    }

    @Provides @Singleton fun provideApiManager(): ApiManager {
        return ApiManager()
    }

    @Provides
    @Singleton
    fun provideRealmManager(): RealmManager {
        return RealmManager()
    }
}
