package makes.flint.poh.data.dataController.callbacks

/**
 * RepositoryCallbackSingle
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
interface RepositoryCallbackSingle<in T> {
    fun onError(error: Throwable)
    fun onRetrieve(refreshed: Boolean, lastSync: String, result: T)
}
