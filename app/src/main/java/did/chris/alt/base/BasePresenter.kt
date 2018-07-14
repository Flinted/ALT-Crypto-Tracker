package did.chris.alt.base

import org.jetbrains.anko.AnkoLogger

/**
 * BasePresenter
 * Copyright © 2018 ChrisDidThis.. All rights reserved.
 */
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
