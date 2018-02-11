package makes.flint.poh.data.tracker

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import makes.flint.poh.data.services.interfaces.RealmDeletable
import java.util.*

/**
 * TrackerDataEntry
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
open class TrackerDataEntry constructor() : RealmObject(), RealmDeletable {

    @PrimaryKey
    internal var id = UUID.randomUUID().toString()
    internal lateinit var symbol: String
    internal lateinit var name: String
    internal var transactions: RealmList<TrackerDataTransaction> = RealmList()

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
