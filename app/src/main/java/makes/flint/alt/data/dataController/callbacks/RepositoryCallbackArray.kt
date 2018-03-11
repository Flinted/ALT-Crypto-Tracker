package makes.flint.alt.data.dataController.callbacks

/**
 * RepositoryCallbackArray
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface RepositoryCallbackArray<in T> {

    // Functions

    fun onError(error: Throwable)
    fun onRetrieve(refreshed: Boolean, lastSync: String, results: List<T>)
}
