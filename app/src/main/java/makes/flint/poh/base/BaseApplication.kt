package makes.flint.poh.base

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import makes.flint.poh.injection.components.DaggerDataComponent
import makes.flint.poh.injection.components.DaggerPresenterComponent
import makes.flint.poh.injection.components.DataComponent
import makes.flint.poh.injection.components.PresenterComponent
import makes.flint.poh.injection.modules.DataModule
import makes.flint.poh.injection.modules.PresenterModule

/**
 * BaseApplication
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
class BaseApplication : Application() {

    private var dataComponent: DataComponent? = null
    private var presenterComponent: PresenterComponent? = null

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        initialiseRealm()
    }

    private fun initialiseRealm() {
        Realm.init(this)
    }

    fun getAppComponent(): DataComponent = dataComponent ?: initialiseDataComponent()

    fun getPresenterComponent(): PresenterComponent = presenterComponent ?: initialisePresenterComponent()

    private fun initialisePresenterComponent(): PresenterComponent {
        presenterComponent = DaggerPresenterComponent.builder().presenterModule(PresenterModule()).build()
        return presenterComponent as PresenterComponent
    }

    private fun initialiseDataComponent(): DataComponent {
        dataComponent = DaggerDataComponent.builder().dataModule(DataModule()).build()
        return dataComponent as DataComponent
    }
}