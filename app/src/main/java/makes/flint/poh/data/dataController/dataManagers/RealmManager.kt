package makes.flint.poh.data.dataController.dataManagers

import io.realm.Realm
import io.realm.RealmObject
import makes.flint.poh.data.dataController.dataSource.DataSource
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import javax.inject.Inject

/**
 * RealmManager
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
class RealmManager @Inject constructor() : DataSource {

    private var realm = Realm.getDefaultInstance()

    fun open() {
        if (realm.isClosed) {
            realm = Realm.getDefaultInstance()
        }
    }

    fun close() {
        if (!realm.isClosed) {
            realm.close()
        }
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

    fun <T : RealmObject> copyOrUpdate(itemToCopy: T) {
        open()
        realm.executeTransaction {
            realm.copyToRealmOrUpdate(itemToCopy)
        }
    }

    fun getFavouriteCoins(): MutableList<FavouriteCoin> {
        open()
        val results = realm.where(FavouriteCoin::class.java).findAll()
        results?.let {
            return realm.copyFromRealm(results)
        }
        return mutableListOf()
    }

    fun getFavouriteCoin(symbol: String): FavouriteCoin? {
        open()
        return realm.where(FavouriteCoin::class.java)
                .equalTo("symbol", symbol)
                .findFirst()
    }

    fun <T : RealmObject> remove(itemToRemove: T) {
        open()
        realm.executeTransaction {
            itemToRemove.deleteFromRealm()
        }
    }
}