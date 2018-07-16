package did.chris.alt.data.dataController.callbacks


interface RepositoryCallbackSingle<in T> {

    // Functions
    fun onError(error: Throwable)
    fun onRetrieve(refreshed: Boolean, apiResolution: Int, chartResolution: Int, result: T)
}
