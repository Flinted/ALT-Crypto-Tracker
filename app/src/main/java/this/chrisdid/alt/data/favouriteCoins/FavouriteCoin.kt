package this.chrisdid.alt.data.favouriteCoins

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * FavouriteCoin
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

open class FavouriteCoin() : RealmObject() {

    // Properties

    @PrimaryKey
    lateinit var symbol: String

    // Lifecycle

    constructor(symbol: String) : this() {
        this.symbol = symbol
    }
}
