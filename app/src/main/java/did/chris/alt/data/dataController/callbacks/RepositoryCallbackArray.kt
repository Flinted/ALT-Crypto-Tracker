package did.chris.alt.data.dataController.callbacks

interface RepositoryCallbackArray<in T> {

    // Functions
    fun onError(error: Throwable)
    fun onRetrieve(refreshed: Boolean, lastSync: String, results: List<T>)
}
