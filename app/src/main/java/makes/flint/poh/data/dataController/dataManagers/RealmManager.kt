package makes.flint.poh.data.dataController.dataManagers

import io.realm.Realm
import makes.flint.poh.data.dataController.dataSource.DataSource
import javax.inject.Inject

/**
 * RealmManager
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
class RealmManager @Inject constructor() : DataSource {

    private lateinit var realm: Realm

    override fun open() {
        realm = Realm.getDefaultInstance()
    }

    override fun close() {
        realm.close()
    }

    fun beginTransaction() {
        realm.beginTransaction()
    }

    fun commitTransaction() {
        realm.commitTransaction()
    }

    fun cancelTransaction() {
        realm.cancelTransaction()
    }
}