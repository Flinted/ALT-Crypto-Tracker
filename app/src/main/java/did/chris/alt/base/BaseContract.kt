package did.chris.alt.base

interface BaseContractView {
    fun showLoading()

    fun hideLoading()

    fun showError(stringId: Int?)
}


interface BaseContractPresenter<in V : BaseContractView> {
    fun attachView(view: V)

    fun detachView()

    fun initialise()
}
