package makes.flint.poh.data.dataController.dataManagers

import io.realm.Realm
import io.realm.RealmObject
import makes.flint.poh.data.dataController.dataSource.DataSource
import makes.flint.poh.data.favouriteCoins.FavouriteCoin
import makes.flint.poh.data.tracker.TrackerEntryData
import makes.flint.poh.data.tracker.TrackerTransaction
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

    fun getCopyOfTrackerEntry(coinName: String, symbol: String): TrackerEntryData? {
        open()
        val result = realm.where(TrackerEntryData::class.java)
                .equalTo("name", coinName)
                .equalTo("symbol", symbol)
                .findFirst()
        result ?: return null
        return realm.copyFromRealm(result)
    }

    fun getAllTrackerDataEntries(): List<TrackerEntryData> {
        open()
        val results = realm.where(TrackerEntryData::class.java)
                .findAll()
        results ?: return emptyList()
        return realm.copyFromRealm(results)
    }

    fun deleteTrackerEntryDataFor(id: String) {
        open()
        realm.executeTransaction {
            val entry = realm.where(TrackerEntryData::class.java)
                    .equalTo("id", id)
                    .findFirst()
            entry?.nestedDeleteFromRealm()
        }
    }

    fun deleteTransactionFor(id: String) {
        open()
        realm.executeTransaction {
            val entry = realm.where(TrackerTransaction::class.java)
                    .equalTo("id", id)
                    .findFirst()
            entry?.nestedDeleteFromRealm()
        }
    }
}