package makes.flint.alt.data.tracker

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import makes.flint.alt.data.TimeStamp
import makes.flint.alt.data.services.interfaces.RealmDeletable
import java.util.*

/**
 * TrackerTransaction
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
const val TRANSACTION_BUY = "TransactionBuy"
const val TRANSACTION_SELL = "TransactionSell"
const val TRANSACTION_MINED = "TransactionMined"

open class TrackerDataTransaction : RealmObject(), RealmDeletable {

    @PrimaryKey
    internal var id = UUID.randomUUID().toString()
    internal lateinit var transactionType: String
    internal lateinit var purchaseDate: TimeStamp
    internal lateinit var loggedDate: TimeStamp
    internal lateinit var quantity: String
    internal var pricePaid: String? = null
    internal var fees: String? = null
    internal var lastEditedDate: TimeStamp? = null
    internal var exchange: String? = null
    internal var notes: String? = null

    override fun nestedDeleteFromRealm() {
        purchaseDate.nestedDeleteFromRealm()
        loggedDate.nestedDeleteFromRealm()
        lastEditedDate?.nestedDeleteFromRealm()
        this.deleteFromRealm()
    }
}
