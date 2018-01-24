package makes.flint.poh.Injection.Modules

import dagger.Module
import dagger.Provides
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.main.MainPresenter

/**
 * DataModule
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
@Module open class PresenterModule {

    @Provides
    fun provideMainPresenter(dataController: DataController): MainPresenter {
        return MainPresenter(dataController)
    }

}