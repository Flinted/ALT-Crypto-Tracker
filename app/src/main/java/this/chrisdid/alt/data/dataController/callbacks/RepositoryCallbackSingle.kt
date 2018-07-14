package this.chrisdid.alt.data.dataController.callbacks

/**
 * RepositoryCallbackSingle
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface RepositoryCallbackSingle<in T> {

    // Functions

    fun onError(error: Throwable)
    fun onRetrieve(refreshed: Boolean, apiResolution: Int, chartResolution: Int, result: T)
}
