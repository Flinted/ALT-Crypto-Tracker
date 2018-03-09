package makes.flint.alt.data.tracker

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import makes.flint.alt.data.TimeStamp
import makes.flint.alt.data.services.interfaces.RealmDeletable
import java.util.*

/**
 * TrackerTransaction
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
const val TRANSACTION_BUY = "TransactionBuy"
const val TRANSACTION_SELL = "TransactionSell"
const val TRANSACTION_MINED = "TransactionMined"

open class TrackerDataTransaction : RealmObject(), RealmDeletable {

    // Properties

    @PrimaryKey
    var id = UUID.randomUUID().toString()
    lateinit var transactionType: String
    lateinit var purchaseDate: TimeStamp
    lateinit var loggedDate: TimeStamp
    lateinit var quantity: String
    var pricePaid: String? = null
    var fees: String? = null
    var lastEditedDate: TimeStamp? = null
    var exchange: String? = null
    var notes: String? = null

    // Overrides

    override fun nestedDeleteFromRealm() {
        purchaseDate.nestedDeleteFromRealm()
        loggedDate.nestedDeleteFromRealm()
        lastEditedDate?.nestedDeleteFromRealm()
        this.deleteFromRealm()
    }
}
