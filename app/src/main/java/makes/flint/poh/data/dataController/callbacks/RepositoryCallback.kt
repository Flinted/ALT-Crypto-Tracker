package makes.flint.poh.data.dataController.callbacks

/**
 * RepositoryCallback
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
interface RepositoryCallback<in T> {
    fun onError(error: Throwable)
    fun onRetrieve(refreshed: Boolean, lastSync: String, results: List<T>)
}