package makes.flint.alt.data.dataController.dataManagers

import io.realm.Realm
import io.realm.RealmObject
import makes.flint.alt.configuration.SettingsData
import makes.flint.alt.data.dataController.dataSource.DataSource
import makes.flint.alt.data.favouriteCoins.FavouriteCoin
import makes.flint.alt.data.tracker.TrackerDataEntry
import makes.flint.alt.data.tracker.TrackerDataTransaction
import javax.inject.Inject

/**
 * RealmManager
 * Copyright © 2018 ChrisDidThis.. All rights reserved.
 */
class RealmManager @Inject constructor() : DataSource {

    // Properties

    private var realm = Realm.getDefaultInstance()

    // Internal Functions

    internal fun open() {
        if (realm.isClosed) {
            realm = Realm.getDefaultInstance()
        }
    }

    internal fun close() {
        if (!realm.isClosed) {
            realm.close()
        }
    }

    internal fun beginTransaction() {
        realm.beginTransaction()
    }

    internal fun commitTransaction() {
        realm.commitTransaction()
    }

    internal fun cancelTransaction() {
        realm.cancelTransaction()
    }

    internal fun <T : RealmObject> copyOrUpdate(itemToCopy: T) {
        open()
        realm.executeTransaction {
            realm.copyToRealmOrUpdate(itemToCopy)
        }
    }

    internal fun getFavouriteCoins(): MutableList<FavouriteCoin> {
        open()
        val results = realm.where(FavouriteCoin::class.java).findAll()
        results?.let {
            return realm.copyFromRealm(results)
        }
        return mutableListOf()
    }

    internal fun getFavouriteCoin(symbol: String): FavouriteCoin? {
        open()
        return realm.where(FavouriteCoin::class.java)
                .equalTo("symbol", symbol)
                .findFirst()
    }

    internal fun <T : RealmObject> remove(itemToRemove: T) {
        open()
        realm.executeTransaction {
            itemToRemove.deleteFromRealm()
        }
    }

    internal fun getCopyOfTrackerEntry(coinName: String, symbol: String): TrackerDataEntry? {
        open()
        val result = realm.where(TrackerDataEntry::class.java)
                .equalTo("name", coinName)
                .equalTo("symbol", symbol)
                .findFirst()
        result ?: return null
        return realm.copyFromRealm(result)
    }

    internal fun getAllTrackerDataEntries(): List<TrackerDataEntry> {
        open()
        val results = realm.where(TrackerDataEntry::class.java)
                .findAll()
        results ?: return emptyList()
        return realm.copyFromRealm(results)
    }

    internal fun deleteTrackerEntryDataFor(id: String) {
        open()
        realm.executeTransaction {
            val entry = realm.where(TrackerDataEntry::class.java)
                    .equalTo("id", id)
                    .findFirst()
            entry?.nestedDeleteFromRealm()
        }
    }

    internal fun deleteTransactionFor(id: String) {
        open()
        realm.executeTransaction {
            val entry = realm.where(TrackerDataTransaction::class.java)
                    .equalTo("id", id)
                    .findFirst()
            entry?.nestedDeleteFromRealm()
        }
    }

    internal fun getSettings(): SettingsData? {
        return realm.where(SettingsData::class.java).findFirst()
    }

    internal fun updateAsFirstLoadComplete() {
        val settings = getSettings()
        realm.executeTransaction {
            settings?.firstLoad = false
            realm.copyToRealmOrUpdate(settings)
        }
    }
}