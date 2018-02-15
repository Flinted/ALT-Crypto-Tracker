package makes.flint.alt.data.favouriteCoins

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * FavouriteCoin
 * Copyright © 2018 Flint Makes. All rights reserved.
 */

open class FavouriteCoin() : RealmObject() {
    @PrimaryKey
    lateinit var symbol: String

    constructor(symbol: String) : this() {
        this.symbol = symbol
    }
}
