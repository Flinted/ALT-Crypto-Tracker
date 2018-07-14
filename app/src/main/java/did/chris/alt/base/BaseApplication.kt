package did.chris.alt.base

import android.app.Application
import com.github.mikephil.charting.utils.Utils
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import io.realm.RealmConfiguration
import did.chris.alt.R
import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.injection.components.DaggerDataComponent
import did.chris.alt.injection.components.DaggerPresenterComponent
import did.chris.alt.injection.components.DataComponent
import did.chris.alt.injection.components.PresenterComponent
import did.chris.alt.injection.modules.DataModule
import did.chris.alt.injection.modules.PresenterModule

/**
 * BaseApplication
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class BaseApplication : Application() {

    // Properties

    private var dataComponent: DataComponent? = null
    private var presenterComponent: PresenterComponent? = null

    // Overrides

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Utils.init(this)
        ALTSharedPreferences.initialise(this)
        initialiseRealm()
    }

    // Internal Functions

    internal fun getAppComponent(): DataComponent = dataComponent ?: initialiseDataComponent()

    internal fun getPresenterComponent(): PresenterComponent =
        presenterComponent ?: initialisePresenterComponent()

    // Private Functions

    private fun initialisePresenterComponent(): PresenterComponent {
        presenterComponent =
                DaggerPresenterComponent.builder().presenterModule(PresenterModule()).build()
        return presenterComponent as PresenterComponent
    }

    private fun initialiseDataComponent(): DataComponent {
        dataComponent = DaggerDataComponent.builder().dataModule(DataModule()).build()
        return dataComponent as DataComponent
    }

    private fun initialiseRealm() {
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}