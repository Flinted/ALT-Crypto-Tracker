package makes.flint.poh.injection.components

import dagger.Component
import makes.flint.poh.injection.modules.DataModule
import javax.inject.Singleton

/**
 * DataComponent
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
@Singleton
@Component(modules = arrayOf(DataModule::class))
interface DataComponent {
}