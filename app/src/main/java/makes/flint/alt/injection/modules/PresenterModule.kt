package makes.flint.alt.injection.modules

import dagger.Module
import dagger.Provides
import makes.flint.alt.data.dataController.DataController
import makes.flint.alt.ui.main.MainPresenter

/**
 * DataModule
 * Copyright © 2018 ChrisDidThis.. All rights reserved.
 */
@Module open class PresenterModule {

    @Provides
    fun provideMainPresenter(dataController: DataController): MainPresenter {
        return MainPresenter(dataController)
    }

}