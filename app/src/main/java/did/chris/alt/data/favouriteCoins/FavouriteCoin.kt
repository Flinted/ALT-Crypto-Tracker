package did.chris.alt.data.favouriteCoins

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FavouriteCoin() : RealmObject() {

    // Properties
    @PrimaryKey
    lateinit var symbol: String

    // Lifecycle
    constructor(symbol: String) : this() {
        this.symbol = symbol
    }
}
