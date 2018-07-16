package did.chris.alt.base

import org.jetbrains.anko.AnkoLogger

abstract class BasePresenter<T : BaseContractView> : BaseContractPresenter<T>, AnkoLogger {

    // Properties

    protected var view: T? = null

    // Overrides

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}
