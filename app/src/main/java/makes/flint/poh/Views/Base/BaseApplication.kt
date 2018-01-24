package makes.flint.poh.Views.Base

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import io.realm.RealmConfiguration
import makes.flint.poh.Injection.Components.DaggerDataComponent
import makes.flint.poh.Injection.Components.DaggerPresenterComponent
import makes.flint.poh.Injection.Components.DataComponent
import makes.flint.poh.Injection.Components.PresenterComponent
import makes.flint.poh.Injection.Modules.DataModule
import makes.flint.poh.Injection.Modules.PresenterModule

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
        val realmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
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