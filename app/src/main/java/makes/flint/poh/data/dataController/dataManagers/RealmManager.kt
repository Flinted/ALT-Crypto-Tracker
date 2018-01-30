package makes.flint.poh.data.dataController.dataManagers

import io.realm.Realm
import io.realm.RealmObject
import makes.flint.poh.data.CMCTimeStamp
import makes.flint.poh.data.dataController.dataSource.DataSource
import javax.inject.Inject

/**
 * RealmManager
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
class RealmManager @Inject constructor() : DataSource {

    private lateinit var realm: Realm

    fun open() {
        realm = Realm.getDefaultInstance()
    }

    fun close() {
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

    fun lastSyncCMCGET(): CMCTimeStamp? {
        open()
        val timeStamp = realm.where(CMCTimeStamp::class.java).findFirst()
        return timeStamp
    }

    fun <T : RealmObject> copyOrUpdate(itemToCopy: T) {
        realm.copyToRealmOrUpdate(itemToCopy)
    }
}