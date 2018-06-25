package makes.flint.alt.base

import android.support.v4.app.Fragment
import android.widget.Toast
import makes.flint.alt.errors.ErrorHandler
import makes.flint.alt.injection.components.PresenterComponent

/**
 * BaseFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
open class BaseFragment : Fragment() {

    // Properties

    internal var presenter: BaseContractPresenter<*>? = null

    // Overrides

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    // Protected Functions

    protected fun getBaseApplication(): BaseApplication = activity?.application as BaseApplication

    protected fun getPresenterComponent(): PresenterComponent = getBaseApplication().getPresenterComponent()

    protected fun attachPresenter(presenter: BaseContractPresenter<*>) {
        this.presenter = presenter
    }

    // Internal Functions

    internal fun showToast(stringId: Int, toastLength: Int) {
        Toast.makeText(context, getString(stringId), toastLength).show()
    }

    open fun showLoading() {

    }

    open fun hideLoading() {

    }

    open fun showError(stringId: Int?) {
        ErrorHandler.showError(activity, stringId)
    }
}