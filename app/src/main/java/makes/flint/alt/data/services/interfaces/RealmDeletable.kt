package makes.flint.alt.data.services.interfaces

/**
 * RealmDeletable
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface RealmDeletable {
    // Realm does not do nested deletes,  object must delete their own children.
    fun nestedDeleteFromRealm()
}