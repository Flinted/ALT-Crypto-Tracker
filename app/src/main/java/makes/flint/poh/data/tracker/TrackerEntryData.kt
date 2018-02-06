package makes.flint.poh.data.tracker

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import makes.flint.poh.data.services.interfaces.RealmDeletable
import java.util.*

/**
 * TrackerEntryData
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
open class TrackerEntryData constructor() : RealmObject(), RealmDeletable {

    @PrimaryKey
    internal var id = UUID.randomUUID().toString()
    internal lateinit var symbol: String
    internal lateinit var name: String
    internal var transactions: RealmList<TrackerTransaction> = RealmList()

    constructor(symbol: String, name: String) : this() {
        this.symbol = symbol
        this.name = name
    }

    override fun nestedDeleteFromRealm() {
        var count = transactions.size - 1
        while (count >= 0) {
            transactions[count].nestedDeleteFromRealm()
            count--
        }
        this.deleteFromRealm()
    }
}
