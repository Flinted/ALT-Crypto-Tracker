package this.chrisdid.alt.base

/**
 * BaseContract
 * Copyright © 2018 ChrisDidThis.. All rights reserved.
 */

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
