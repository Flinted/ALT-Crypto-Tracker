package makes.flint.poh.Injection.Components

import dagger.Component
import makes.flint.poh.Injection.Modules.PresenterModule
import makes.flint.poh.main.MainPresenter
import javax.inject.Singleton

/**
 * PresenterComponent
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
@Singleton
@Component(modules = arrayOf(PresenterModule::class))
interface PresenterComponent {
    fun provideMainPresenter(): MainPresenter
}