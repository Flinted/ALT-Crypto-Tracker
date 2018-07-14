package this.chrisdid.alt.injection.components

import dagger.Component
import this.chrisdid.alt.injection.modules.DataModule
import javax.inject.Singleton

/**
 * DataComponent
 * Copyright © 2018 ChrisDidThis.. All rights reserved.
 */
@Singleton
@Component(modules = arrayOf(DataModule::class))
interface DataComponent {
}