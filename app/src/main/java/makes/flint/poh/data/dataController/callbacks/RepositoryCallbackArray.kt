package makes.flint.poh.data.dataController.callbacks

/**
 * RepositoryCallbackArray
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface RepositoryCallbackArray<in T> {
    fun onError(error: Throwable)
    fun onRetrieve(refreshed: Boolean, lastSync: String, results: List<T>)
}
